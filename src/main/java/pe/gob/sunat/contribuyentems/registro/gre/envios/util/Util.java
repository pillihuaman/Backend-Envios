package pe.gob.sunat.contribuyentems.registro.gre.envios.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.gob.sunat.contribuyentems.registro.gre.envios.util.exceptions.ErrorEnum;
import pe.gob.sunat.contribuyentems.registro.gre.shared.utils.exceptions.UnprocessableEntityException;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.util.config.bean.MensajePersonalizadoBean;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.util.config.bean.ParametroBean;

public final class Util {

    public static final String defaultFomatDate = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String defaultFomatDateTZ = "yyyy-MM-dd HH:mm:ss.SSS ZZZ";

    /**
     * Utility classes should not have public constructors
     */
    private Util() {
    }

    public static boolean cadenaValida(String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            if (!(Character.isLetter(cadena.charAt(i)) || Character.isDigit(cadena.charAt(i))))
                return false;
        }
        return true;
    }

    public static boolean cadenaYYYYMMDDValida(String cadena) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = cadena.substring(7, 8) + '/' + cadena.substring(4, 6) + '/' + cadena.substring(0, 4);
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isNumeric(String str) throws NumberFormatException {
        try {
            Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getFileExtension(String nombreArchivo) {
        return Arrays.stream(nombreArchivo.split("\\.")).reduce((a, b) -> b).orElse(null);
    }

    public static Date newDate(int anio, int mes, int dia, int hora, int minuto, int segundo, int milisegundo) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, anio);
        date.set(Calendar.MONTH, mes - 1);
        date.set(Calendar.DAY_OF_MONTH, dia);

        date.set(Calendar.HOUR_OF_DAY, hora);
        date.set(Calendar.MINUTE, minuto);
        date.set(Calendar.SECOND, segundo);
        date.set(Calendar.MILLISECOND, milisegundo);

        return date.getTime();
    }

    public static String convertDateToString(Date date) {
        return convertDateToString(date, defaultFomatDate);
    }

    public static String convertDateToString(Date date, String sFormato) {
        SimpleDateFormat formatDate = new SimpleDateFormat(sFormato);
        String sDate = null;

        if (date != null) {
            sDate = formatDate.format(date);
        }
        return sDate;
    }

    public static Date convertToDate(String sFecha, String sFormato) {
        SimpleDateFormat dateParser = new SimpleDateFormat(sFormato, Locale.US);
        Date date = null;
        try {
            date = dateParser.parse(sFecha);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertObjectToJson(Object o) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isDate(String sFecha, String sFormato) {
        boolean bIsDate = true;
        SimpleDateFormat dateParser = new SimpleDateFormat(sFormato, Locale.US);
        try {
            dateParser.parse(sFecha);
        } catch (Exception e) {
            bIsDate = false;
        }
        return bIsDate;

    }

    private static final ResourceBundle errorMessagesResourceBundle = ResourceBundle.getBundle("pe.gob.sunat.contribuyentems.servicio.controlcpe.registro.envios.ws.rest.ErrorNegocio");

    public static String getMensajeErrorNegocio(String codError) throws Exception {
        String mensaje = "Se presentaron errores generales de validacion";
        try {
            mensaje = errorMessagesResourceBundle.getString(codError);
        } catch (Exception e) {
            throw new Exception("errorMessagesResourceBundle.getString");
        }
        return mensaje;
    }

    public static ParametroBean getBusinessValidationMessage(String codBusinessValidation) throws Exception {
        ParametroBean resp;
        resp = new ParametroBean(codBusinessValidation, Util.getMensajeErrorNegocio(codBusinessValidation));
        return resp;
    }

    public static Response responseBusinessValidation(List<ParametroBean> listaErrores) {
        MensajePersonalizadoBean msgr = new MensajePersonalizadoBean(Integer.toString(Constantes.STATUS_CODE_422), Constantes.GENERIC_MESSAGE_422, listaErrores);
        return Response.status(Constantes.STATUS_CODE_422).entity(msgr).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();

    }


    public static int obtenerAnioActual() {
        return LocalDate.now().getYear();
    }
}
