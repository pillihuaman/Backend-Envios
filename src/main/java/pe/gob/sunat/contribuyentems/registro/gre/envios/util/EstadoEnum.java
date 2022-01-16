package pe.gob.sunat.contribuyentems.registro.gre.envios.util;

import org.apache.commons.lang3.StringUtils;

import pe.gob.sunat.contribuyentems.registro.gre.envios.util.exceptions.ErrorEnum;

public enum EstadoEnum {

    PENDIENTE(01,"01", "PENDIENTE"),
    DISCONFORME(02,"02", "DISCONFORME"),
    SUBSANADO(03,"03", "SUBSANADO"),
    CONFORME(04,"04", "CONFORME"),
    NO_VALIDO(05,"05", "NO_VALIDO"),
	PENDIENTE_POR_REINICIO(06,"06", "PENDIENTE POR REINICIO");


    private EstadoEnum(int code, String codeStr, String msg) {
        this.code = code;
        this.codeStr = codeStr;
        this.msg = msg;
    }

    private int code;
    private String codeStr;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public static String getMsg(int code) {
        String msg = StringUtils.EMPTY;

        for (EstadoEnum enumValue : EstadoEnum.values()) {
            if (enumValue.code == code) {
                msg = enumValue.msg;
                break;
            }
        }
        return msg;
    }

}
