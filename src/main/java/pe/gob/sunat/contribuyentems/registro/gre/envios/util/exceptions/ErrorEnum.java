package pe.gob.sunat.contribuyentems.registro.gre.envios.util.exceptions;

import org.apache.commons.lang3.StringUtils;

public enum ErrorEnum {

	ERROR_100(100, "Numero de RUC no enviado o es vacio"),
	ERROR_101(101, "Solo se permite dato numrico de 11 dgitos para el Numero de RUC"),
	ERROR_102(102, "Codigo de comprobante no enviado o es vacio"),
	ERROR_103(103, "Codigo de comprobante no permitido o no valido"),
	ERROR_104(104, "Numero de serie del comprobante no enviado o es vacio"),
	ERROR_105(105, "Numero de serie del comprobante no permitido o no valido"),
	ERROR_106(106, "Numero de comprobante no enviado o es vacio."),
	ERROR_107(107, "Numero de comprobante tiene tipo de dato o formato no valido. Solo se permite dato numrico de 1 hasta 8 dgitos"),
	ERROR_108(108, "El Comprobante no existe"),

	ERROR_115(115, "Codigo del estado de la condicin no enviado o es vacio"),
	ERROR_116(116, "Codigo del estado de la condicin no permitido"),

	ERROR_150(150, "Fecha de emision inicio del comprobante no enviado o es vacio"),
	ERROR_151(151, "Formato de fecha de emision inicio no permitido o no valido para la fecha de modificacin"),
	ERROR_152(152, "Fecha de emision fin del comprobante no enviado o es vacio"),
	ERROR_153(153, "Formato de fecha de emision  fin  no permitido o no valido para la fecha de modificacin"),
	ERROR_154(154, "Codigo de la forma de pago no enviado o es vacio"),
	ERROR_155(155, "Codigo de la forma de pago no permitido"),

	ERROR_158(158, "Numero de RUC del emisor no enviado o es vacio"),
	ERROR_159(159, "Solo se permite dato numrico de 11 dgitos para el Numero de RUC del emisor"),

	ERROR_160(160, "Fecha de Emision inicio del comprobante no enviado o es vacío"),
	ERROR_164(164, "FIndicador de tipo de registro no enviado o es vacío"),
	ERROR_165(165, "Indicador de tipo de registro no permitido"),

	ERROR_171(171, "Archivo ya existe"),
	ERROR_172(172, "Archivo no enviado o es vacio"),
	ERROR_173(173, "Nombre del archivo no enviado o es vacio"),
	ERROR_174(174, "Solo se permite dato alfanumerico de 31 digitos"),
	ERROR_175(175, "Indicador de tipo de fecha  no enviado o es vacio"),
	ERROR_176(176, "Indicador de tipo de fecha no valido"),
	ERROR_177(177, "Codigo de tipo de documento del adquiriente  no enviado o es vacio"),
	ERROR_178(178, "Codigo de tipo de documento del adquiriente no valido"),
	ERROR_179(179, "Numero de documento del adquiriente no enviado o vacio"),

	ERROR_180(180, "Numero de documento del adquiriente supera los 15 caracteres"),

	ERROR_GENERICO(999, "Mensaje de error generico");

	private ErrorEnum(int code, String msg) {
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

		for (ErrorEnum enumValue : ErrorEnum.values()) {
			if (enumValue.code == code) {
				msg = enumValue.msg;
				break;
			}
		}

		return msg;
	}
}
