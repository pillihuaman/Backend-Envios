package pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import pe.gob.sunat.contribuyentems.registro.gre.envios.service.EnviosNoConformidadService;
import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.EnviosNoConformidadRequestDTO;
import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.RegEnvioResponseDTO;
import pe.gob.sunat.tecnologiams.arquitectura.framework.common.util.ConstantesUtils;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.util.UtilLog;

@Path("v1/contribuyente/servicio/controlcpe/e")
public class ComprobanteNoConformidadRestService {

	@Inject
	private UtilLog utilLog;

	@Inject
	private EnviosNoConformidadService comprobanteService;

	@POST
	@Path(value = "/enviosmasivo/{numRuc}/{indTipoRegistro}")
	@Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_OCTET_STREAM })
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarCargaMasiva(@FormDataParam("archivo") InputStream inStream,
			@FormDataParam("archivo") FormDataContentDisposition fileDetail, @PathParam("numRuc") String numRuc,
			@PathParam("indTipoRegistro") String indTipoRegistro) throws Exception {

		utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "Iniciando ComprobanteNoConformidadRestService Rest....zph");
		EnviosNoConformidadRequestDTO enviosMasivosRequestDTO = new EnviosNoConformidadRequestDTO();
		enviosMasivosRequestDTO.setArchivoCargado(inStream);
		enviosMasivosRequestDTO.setNumRuc(numRuc);
		enviosMasivosRequestDTO.setInTipoRegistro(indTipoRegistro);
		enviosMasivosRequestDTO.setArchivoDetalle(fileDetail);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int count;
		while ((count = inStream.read(bytes)) > 0) {
			out.write(bytes, 0, count);
		}
		enviosMasivosRequestDTO.setTamanioArchivo(out.size());
		out.reset();
		out.close();
		out=null;
		
		RegEnvioResponseDTO response = comprobanteService.enviarArchivoNoConformidad(enviosMasivosRequestDTO);
		utilLog.imprimirLog(ConstantesUtils.LEVEL_INFO, "Iniciando ComprobanteNoConformidadRestService Rest....zph");
		return Response.ok(response).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name()))
				.build();

	}

}
