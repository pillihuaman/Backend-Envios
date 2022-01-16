package pe.gob.sunat.contribuyentems.registro.gre.envios.util.exceptions;

import org.apache.commons.lang3.StringUtils;

public enum HTTPErrorEnum {

	HTTP_ERROR_500(500, "Internal Server Error - Se presento una condici\u00F3n inesperada que impidi\u00F3 completar el Request"),
	HTTP_ERROR_400(400, "Bad Request - El Request no puede ser entendido por el Servidor debido a errores de sintaxis, El cliente no debe repetir el Request sin modificaciones"),
	HTTP_ERROR_401(401, "Unauthorized - Fallo en la autenticaci\u00F3n del Cliente"),
	HTTP_ERROR_403(403, "Forbidden - El Cliente no tiene autorizaci\u00F3n para acceder al Recurso"),
	HTTP_ERROR_404(404, "Not Found - El Recurso Solicitado no puede ser encontrado"),
	HTTP_ERROR_405(405, "Method Not Allowed - La ruta especificada no existe"),
	HTTP_ERROR_422(422, "Unprocessable Entity - Se presentaron errores de validaci\u00F3n que impidieron completar el Request");

	private HTTPErrorEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private int code;
	private String msg;

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static String getMsg(int code) {
		String msg = StringUtils.EMPTY;

		for (HTTPErrorEnum enumValue : HTTPErrorEnum.values()) {
			if (enumValue.code == code) {
				msg = enumValue.msg;
				break;
			}
		}

		return msg;
	}

}
