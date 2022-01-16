package pe.gob.sunat.contribuyentems.registro.gre.envios.util;

import org.apache.commons.lang3.StringUtils;

public enum EnumErrores {
	ERROR_1001 (1001 , "1001 -Debe ingresar el archivo a enviar",null),
	ERROR_1002 (1002 , "1002 -Solo está permitido cargar archivos de tipo TXT (.txt)",null),
	ERROR_1011 (1011 , "1011 -El tamaño máximo del archivo no debe superar a 1 Mb",null),
	//ERROR_1005 (1005 , "1005 -El archivo <nombre del archivo txt> fue previamente enviado",null),
	ERROR_1005 (1005 , "1005 -Error con el nombre del archivo, verifique las reglas de nomenclatura del archivo",null),
	ERROR_1006 (1006 , "1006 -Error con el nombre del archivo, el número de RUC del archivo no corresponde con el número de RUC del contribuyente",null),
	ERROR_1007 (1007 , "1007 -Error con el nombre del archivo, el identificador del archivo debe ser <<identificador del archivo>>",null),
	ERROR_1008 (1008 , "1008 -Error con el nombre del archivo, la fecha de envío no corresponde a una fecha valida",null),
	ERROR_1009 (1009 , "1009 -Error con el nombre del archivo, la fecha de envío debe ser igual a la fecha actual",null),
	ERROR_1010 (1010 , "1010 -Error con el nombre del archivo, el número correlativo del archivo debe ser numérico y debe tener entre 1 y hasta 2 caracteres",null),

	ERROR_1003 (1003 , "1003 -El archivo <nombre del archivo txt> fue previamente enviado",null),
	ERROR_1012 (1012 , "1012 -Ocurrió un problema al enviar el archivo",null);
	private EnumErrores(int code, String msg, String exc) {
		this.code = code;
		this.msg = msg;
		this.exc = exc;
	}

	private int code;
	private String msg;
	private String exc;

	public int getCodigo() {
		return code;
	}

	public String getMensaje() {
		return msg;
	}
	
	public String getExcepcion() {
		return exc;
	}
	

	public static String getMensaje(int code) {
		String msg = StringUtils.EMPTY;
				
		for (EnumErrores enumHTTP : EnumErrores.values()) {
			if (enumHTTP.code == code) {
				msg = enumHTTP.msg;
				
				
				break;
			}
		}

		return msg;
	}

}
