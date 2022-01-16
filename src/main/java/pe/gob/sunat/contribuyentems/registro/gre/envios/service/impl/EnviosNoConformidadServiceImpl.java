package pe.gob.sunat.contribuyentems.registro.gre.envios.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Formatter;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoGridFSException;
import com.mongodb.MongoTimeoutException;

import pe.gob.sunat.contribuyentems.registro.gre.envios.domain.Auditoria;
import pe.gob.sunat.contribuyentems.registro.gre.envios.domain.EnviosMasivos;
import pe.gob.sunat.contribuyentems.registro.gre.envios.producer.MigeKafkaProducer;
import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.ComprobanteRepository;
import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.CorrelativosTicketRepository;
import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.EnviosNoConformidadRepository;
import pe.gob.sunat.contribuyentems.registro.gre.envios.service.EnviosNoConformidadService;
import pe.gob.sunat.contribuyentems.registro.gre.envios.util.Constantes;
import pe.gob.sunat.contribuyentems.registro.gre.envios.util.EnumErrores;
import pe.gob.sunat.contribuyentems.registro.gre.envios.util.EstadoEnum;
import pe.gob.sunat.contribuyentems.registro.gre.envios.util.Util;
import pe.gob.sunat.contribuyentems.registro.gre.envios.util.ValidaParametros;
import pe.gob.sunat.contribuyentems.registro.gre.envios.util.ValidatorParams;
import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.EnviosNoConformidadRequestDTO;
import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.RegEnvioResponseDTO;
import pe.gob.sunat.contribuyentems.registro.gre.shared.utils.exceptions.ErrorMessage;
import pe.gob.sunat.contribuyentems.registro.gre.shared.utils.exceptions.UnprocessableEntityException;
import pe.gob.sunat.tecnologia.arquitectura.framework.mongodb.util.JsonUtil;
import pe.gob.sunat.tecnologiams.arquitectura.framework.common.util.ConstantesUtils;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.util.UtilLog;

public class EnviosNoConformidadServiceImpl implements EnviosNoConformidadService {

	@Inject
	private ComprobanteRepository comprobanteRepository;

	@Inject
	private EnviosNoConformidadRepository enviosMasivosRepository;

	@Inject
	private CorrelativosTicketRepository correlativosTicketRepository;

	@Inject
	private MigeKafkaProducer kafkaProducer;

	@Inject
	private UtilLog utilLog;

	@Override
	public RegEnvioResponseDTO enviarArchivoNoConformidad(EnviosNoConformidadRequestDTO request) throws Exception {
		ValidatorParams validatorParams = new ValidatorParams();
		validatorParams = Validar(request);
		String codTicketMasivo = "";
		if (validatorParams.existErrors()) {
			throw new UnprocessableEntityException(validatorParams.getlstErrorMsg());
		} else {

			ValidatorParams validatorParams2 = new ValidatorParams();
			validatorParams2 = ValidaParametros.listaNombreArchivo(request.getArchivoDetalle().getFileName(), request);
			if (validatorParams2.existErrors()) {
				throw new UnprocessableEntityException(validatorParams2.getlstErrorMsg());
			} else {
				utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "Consulta a BD existencia de archivo.");
				boolean archivoExiste = consultarExistenciaArchivo(request.getArchivoDetalle().getFileName());
				utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "Validando existencia de archivo.");
				String idArchivo;
				if (archivoExiste) {
					ValidatorParams validatorParams3 = new ValidatorParams();
					validatorParams3.addErrors(
							new ErrorMessage(EnumErrores.ERROR_1003.getCodigo(), EnumErrores.ERROR_1003.getMensaje()));
					if (validatorParams3.existErrors()) {
						throw new UnprocessableEntityException(validatorParams3.getlstErrorMsg());
					}

				}
				idArchivo = registrarArchivo(request);
				int anioActual = Util.obtenerAnioActual();

				utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "Obteniendo numero correlativo.");
				int correlativoActual = obtenerValorCorrelativo(request.getNumRuc(), anioActual);

				Formatter formatter = new Formatter();
				codTicketMasivo = anioActual + formatter.format("%08d", correlativoActual).toString();

				EnviosMasivos enviosMasivos = new EnviosMasivos();
				enviosMasivos.setId(idArchivo);
				enviosMasivos.setNumRuc(request.getNumRuc());
				enviosMasivos.setCodTicketMasivo(codTicketMasivo);
				enviosMasivos.setIndTipoRegistro(request.getInTipoRegistro());
				enviosMasivos.setIndEstadoEnvio(EstadoEnum.PENDIENTE.getCodeStr());
				enviosMasivos.setNomArchivoPlano(request.getArchivoDetalle().getFileName());
				enviosMasivos.setFecInicioProceso(new Date());
				enviosMasivos.setFecFinProceso(null);
				enviosMasivos.setCntCpe(0);
				enviosMasivos.setCntCorrectos(0);
				enviosMasivos.setCntErrores(0);
				Auditoria auditoria = new Auditoria();
				Date fecAuditoria = new Date();
				String usuAuditoria = "sistema";

				auditoria.setCodUsuModif(usuAuditoria);
				auditoria.setCodUsuRegis(usuAuditoria);
				auditoria.setFecModif(fecAuditoria);
				auditoria.setFecRegis(fecAuditoria);
				enviosMasivos.setAuditoria(auditoria);

				utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "Guardando envio masivo.");
				enviosMasivosRepository.guardarEnviosMasivos(enviosMasivos);

				utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "Enviando mensaje a Kafka.");
				enviarMensajeKafka(enviosMasivos);
			}

		}

		return new RegEnvioResponseDTO(Constantes.CODIGO_OPERACION_OK, Constantes.DES_RESPUESTA_OK, codTicketMasivo);
	}

	private boolean consultarExistenciaArchivo(String fileName) throws UnprocessableEntityException, IOException {
		ValidatorParams validatorParams = new ValidatorParams();
		boolean archivoExiste = false;
		try {
			utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "======archivoExiste=========");
			archivoExiste = Optional.ofNullable(comprobanteRepository.obtenerArchivoPorNombre(fileName)).isPresent();
			utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "archivoExiste==" + archivoExiste);
		} catch (MongoGridFSException me) {
			if (me.getMessage().contains("No file found")) {
				utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "Archivo no existe en base de datos");
				return false;
			}
		} catch (MongoTimeoutException mte) {
			if (mte.getMessage().contains("Timed out")) {
				utilLog.imprimirLog(ConstantesUtils.LEVEL_ERROR, "Error en consulta por Time Out");
				throw new UnprocessableEntityException(validatorParams.getlstErrorMsg());
				// .addError("Error en consulta por Time cccccccccccc a base de datos");
			}
		} catch (Exception e) {
			utilLog.imprimirLog(ConstantesUtils.LEVEL_ERROR, "Error consultando existencia de archivos");
			e.printStackTrace();
			utilLog.imprimirLog(ConstantesUtils.LEVEL_ERROR, e.getMessage());
			throw e;
//            throw new UnprocessableEntityException().addError("Error consultando existencia de archivos");
		}
		return archivoExiste;
	}

	private String registrarArchivo(EnviosNoConformidadRequestDTO request) throws UnprocessableEntityException {
		ValidatorParams validatorParams = new ValidatorParams();
		String idResponse = "";
		utilLog.imprimirLog(ConstantesUtils.LEVEL_DEBUG, "===comprobanteRepository.registrarArchivo===");
		try {
			ObjectId id = comprobanteRepository.registrarArchivo(request.getArchivoCargado(),
					request.getArchivoDetalle().getFileName());
			utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "id====" + id);
			utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "id.toHexString()====" + id.toHexString());
			idResponse = id.toHexString();
			utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "idResponse====" + idResponse);
		} catch (Exception e) {
			e.printStackTrace();
			utilLog.imprimirLog(ConstantesUtils.LEVEL_ERROR, e.getMessage());
			throw new UnprocessableEntityException(validatorParams.getlstErrorMsg());
		}
		return idResponse;
	}

	private Integer obtenerValorCorrelativo(String numRuc, int anio) throws UnprocessableEntityException {
		ValidatorParams validatorParams = new ValidatorParams();
		Integer correlativoActual = null;

		try {
			correlativoActual = correlativosTicketRepository.obtenerValCorrelativo(numRuc, anio);
		} catch (ParseException e) {
			throw new UnprocessableEntityException(validatorParams.getlstErrorMsg());
		}
		return correlativoActual;
	}

	private void enviarMensajeKafka(EnviosMasivos enviosMasivos) throws UnprocessableEntityException {
		ValidatorParams validatorParams = new ValidatorParams();
		ObjectMapper objMapper = JsonUtil.getMapper();
		String jsonString = null;
		try {
			jsonString = objMapper.writeValueAsString(enviosMasivos);
			kafkaProducer.producirMensajeKafka(jsonString);
		} catch (JsonProcessingException e) {
			throw new UnprocessableEntityException(validatorParams.getlstErrorMsg());
		}
	}

	@SuppressWarnings("null")
	public ValidatorParams Validar(EnviosNoConformidadRequestDTO request) throws ParseException {
		ValidatorParams validatorParams = new ValidatorParams();
		// validatorParams =
		// ValidaParametros.listaNombreArchivo(request.getArchivoDetalle().getFileName(),
		// request);
		if (ValidaParametros.validarNombreGeneralArchivo(request.getArchivoDetalle().getFileName())) {
			if (Objects.isNull(request)) {
				if (ValidaParametros.validarVacioNullEmpty(request.getArchivoDetalle().getFileName())) {
					validatorParams.addErrors(
							new ErrorMessage(EnumErrores.ERROR_1001.getCodigo(), EnumErrores.ERROR_1001.getMensaje()));
				}
			}

			if (!ValidaParametros.validarTXT(request.getArchivoDetalle().getFileName())) {
				validatorParams.addErrors(
						new ErrorMessage(EnumErrores.ERROR_1002.getCodigo(), EnumErrores.ERROR_1002.getMensaje()));

			}
			if (!ValidaParametros.validarTXTTamaño(request.getTamanioArchivo())) {
				validatorParams.addErrors(
						new ErrorMessage(EnumErrores.ERROR_1011.getCodigo(), EnumErrores.ERROR_1011.getMensaje()));
			}

			if (!ValidaParametros.validarTXT(request.getArchivoDetalle().getFileName())) {
				if (!ValidaParametros.validarNombreGeneralArchivo(request.getArchivoDetalle().getFileName())) {
					validatorParams.addErrors(
							new ErrorMessage(EnumErrores.ERROR_1002.getCodigo(), EnumErrores.ERROR_1002.getMensaje()));
				}

			}
		} else {
			validatorParams.addErrors(
					new ErrorMessage(EnumErrores.ERROR_1005.getCodigo(), EnumErrores.ERROR_1005.getMensaje()));
		}

		return validatorParams;

	}

}
