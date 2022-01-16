package pe.gob.sunat.contribuyentems.registro.gre.envios.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.regex.Pattern;

import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.EnviosNoConformidadRequestDTO;
import pe.gob.sunat.contribuyentems.registro.gre.shared.utils.exceptions.ErrorMessage;

public class ValidaParametros {

	public static boolean validarRucDigitos(String valor) {
		java.util.regex.Matcher path = Pattern.compile("^[0-9]{11}$").matcher(valor);
		if (path.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validarNumCorrelativo(String valor) {
		String[] listaRequest = valor.split("\\.");
		Queue<String> queue = new LinkedList<>(Arrays.asList(listaRequest));
		String nombreCorrelativo = retrieveElement(0, queue);
		if (validarVacioNullEmpty(nombreCorrelativo)) {
			java.util.regex.Matcher path = Pattern.compile("^[0-9]{1,2}$").matcher(nombreCorrelativo);
			if (path.matches()) {
				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean validarVacioNullEmpty(String valor) {
		if (valor != null && !valor.isEmpty() && !valor.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validarTXT(String valor) {
		java.util.regex.Matcher path = Pattern.compile("^^[a-zA-Z0-9\\s_\\.\\-\\(\\):]+.txt$$").matcher(valor);
		if (path.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validarTXTTamaño(Long size) {
		boolean valida = false;
		long fileSizeInKB = size / Constantes.TAMANIO_CONSTANTE_BYTES;
		long fileSizeInMB = fileSizeInKB / Constantes.TAMANIO_CONSTANTE_BYTES;
		if (size >= 0) {
			if (fileSizeInMB > Constantes.TAMANIO_MAXIMO_MB_TXT) {
				valida = false;
			}else {
				valida = true;
			}
		}
		return valida;
	}

	public static boolean validarNombreGeneralArchivo(String nombreArchivo) {
		java.util.regex.Matcher path = Pattern.compile("^[0-9]{11}.*.txt$")
				.matcher(nombreArchivo);
		if (path.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static ValidatorParams listaNombreArchivo(String nombreArchivo, EnviosNoConformidadRequestDTO reque)
			throws ParseException {
		ValidatorParams validatorParams = new ValidatorParams();
		String[] listaRequest = nombreArchivo.split("-");
		Queue<String> queue = new LinkedList<>(Arrays.asList(listaRequest));

		if (Objects.nonNull(queue)) {
			if (queue.size() == 4) {
				// ruc
				if (!validarRucDigitos(retrieveElement(0, queue))) {
					String ruc = retrieveElement(0, queue);
					if (!ruc.equals(reque.getNumRuc())) {
						validatorParams.addErrors(new ErrorMessage(EnumErrores.ERROR_1006.getCodigo(),
								EnumErrores.ERROR_1006.getMensaje()));
					}

				}
				if (validarRucDigitos(retrieveElement(0, queue))) {
					String ruc = retrieveElement(0, queue);
					if (!ruc.equals(reque.getNumRuc())) {
						validatorParams.addErrors(new ErrorMessage(EnumErrores.ERROR_1006.getCodigo(),
								EnumErrores.ERROR_1006.getMensaje()));
					}

				}

				if (!retrieveElement(1, queue).equals(Constantes.IDENTIFICADOR_ARCHIVO)) {
					String identificadorArchivo = retrieveElement(1, queue);
					validatorParams.addErrors(
							new ErrorMessage(EnumErrores.ERROR_1007.getCodigo(), EnumErrores.ERROR_1007.getMensaje()));
				}
				if (!validarFormatoYYYMMDD(retrieveElement(2, queue))) {
					String identificadorArchivo = retrieveElement(2, queue);
					validatorParams.addErrors(
							new ErrorMessage(EnumErrores.ERROR_1008.getCodigo(), EnumErrores.ERROR_1008.getMensaje()));
				}
				if (validarFormatoYYYMMDD(retrieveElement(2, queue))) {
					if (!validarFormatoYYYMMDDFechaValida(retrieveElement(2, queue))) {
						String fechaactual = retrieveElement(2, queue);
						validatorParams.addErrors(new ErrorMessage(EnumErrores.ERROR_1008.getCodigo(),
								EnumErrores.ERROR_1008.getMensaje()));
					}
				}
				if (validarFormatoYYYMMDD(retrieveElement(2, queue))) {
					if (validarFormatoYYYMMDDFechaValida(retrieveElement(2, queue))) {
						if (!validarFechaActual(retrieveElement(2, queue))) {
							String fechaactual = retrieveElement(2, queue);
							validatorParams.addErrors(new ErrorMessage(EnumErrores.ERROR_1009.getCodigo(),
									EnumErrores.ERROR_1009.getMensaje()));
						}
					}
				}

				if (!validarNumCorrelativo(retrieveElement(3, queue))) {
					String correlativo = retrieveElement(3, queue);
					validatorParams.addErrors(
							new ErrorMessage(EnumErrores.ERROR_1010.getCodigo(), EnumErrores.ERROR_1010.getMensaje()));
				}

			} else {
				validatorParams.addErrors(
						new ErrorMessage(EnumErrores.ERROR_1001.getCodigo(), EnumErrores.ERROR_1001.getMensaje()));
			}

		} else {
			validatorParams.addErrors(
					new ErrorMessage(EnumErrores.ERROR_1001.getCodigo(), EnumErrores.ERROR_1001.getMensaje()));
		}

		return validatorParams;
	}

	public static boolean validarFormatoYYYMMDD(String valor) throws ParseException {
		boolean vaidatedate = false;
		Date dateI = null;
		java.util.regex.Matcher path = Pattern.compile("^\\d{4}\\d{2}\\d{2}$").matcher(valor);
		if (path.matches()) {
			if (isDateValid(valor)) {
				vaidatedate = true;
			}

			else {
				vaidatedate = false;
			}

		} else {
			vaidatedate = false;
		}
		return vaidatedate;
	}

	public static boolean validarFormatoYYYMMDDFechaValida(String valor) throws ParseException {
		boolean vaidatedate = false;
		if (isDateValid(valor)) {
			vaidatedate = true;
		}

		else {
			vaidatedate = false;
		}
		return vaidatedate;
	}

	public static boolean validarFechaActual(String valor) throws ParseException {
		boolean vaidatedate = false;
		if (isDateValid(valor)) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate dateIni = LocalDate.parse(valor, dtf);
			LocalDate hoy = LocalDate.now();
			if (dateIni.isEqual(hoy)) {
				vaidatedate = true;
			} else {
				vaidatedate = false;
			}
		}

		else {
			vaidatedate = false;
		}
		return vaidatedate;
	}

	public static boolean validarFormatoYYYMMDDFechaActual(String valor) {
		Date dateI = null;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		System.out.println(formatter.format(date));

		try {
			dateI = new SimpleDateFormat("yyyy-MM-dd").parse(valor);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!date.before(dateI)) {
			return true;
		} else {
			return false;
		}
	}

	public static String retrieveElement(int index, @SuppressWarnings("rawtypes") Queue q) {
		@SuppressWarnings("rawtypes")
		Iterator it = q.iterator();
		int count = 0;
		while (it.hasNext()) {
			Object e = it.next();
			if (count == index) {
				return e.toString();
			}
			count++;
		}
		return null;
	}

	public static boolean isDateValid(String date) {
		String DATE_FORMAT = "yyyyMMdd";
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
