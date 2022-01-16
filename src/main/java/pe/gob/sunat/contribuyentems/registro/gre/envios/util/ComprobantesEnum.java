package pe.gob.sunat.contribuyentems.registro.gre.envios.util;

import org.apache.commons.lang3.StringUtils;

public enum ComprobantesEnum {

    FACTURA("01", "FACTURA"),
    RHE("02", "RHE"),
    NOTA_CREDITO("07", "NOTA_CREDITO"),
    NOTA_DEBITO("08", "NOTA_DEBITO"),
    NOTA_CREDITO_RHE("R7", "NOTA_CREDITO_RHE");

    private ComprobantesEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsg(String code) {
        String msg = StringUtils.EMPTY;

        for (ComprobantesEnum enumValue : ComprobantesEnum.values()) {
            if (code.equals(enumValue.code)) {
                msg = enumValue.msg;
                break;
            }
        }

        return msg;
    }
}
