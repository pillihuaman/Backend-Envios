package pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto;

public class RegEnvioResponseDTO {
    private String codOperacion;
    private String desRespuesta;
    private String codTicketMasivo;

    public RegEnvioResponseDTO(String codOperacion, String desRespuesta, String codTicketMasivo) {
        this.codOperacion = codOperacion;
        this.desRespuesta = desRespuesta;
        this.codTicketMasivo = codTicketMasivo;
    }

    public String getCodOperacion() {
        return codOperacion;
    }

    public void setCodOperacion(String codOperacion) {
        this.codOperacion = codOperacion;
    }

    public String getDesRespuesta() {
        return desRespuesta;
    }

    public void setDesRespuesta(String desRespuesta) {
        this.desRespuesta = desRespuesta;
    }

    public String getCodTicketMasivo() {
        return codTicketMasivo;
    }

    public void setCodTicketMasivo(String codTicketMasivo) {
        this.codTicketMasivo = codTicketMasivo;
    }
}
