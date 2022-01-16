package pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto;

public class ArchivoComprobanteRequestDTO {

    private String numRuc;
    private String arcContenidoPlano;
    private String nomArchivoPlano;
    private String indTipoRegistro;

    public String getNumRuc() {
        return numRuc;
    }

    public void setNumRuc(String numRuc) {
        this.numRuc = numRuc;
    }

    public String getArcContenidoPlano() {
        return arcContenidoPlano;
    }

    public void setArcContenidoPlano(String arcContenidoPlano) {
        this.arcContenidoPlano = arcContenidoPlano;
    }

    public String getNomArchivoPlano() {
        return nomArchivoPlano;
    }

    public void setNomArchivoPlano(String nomArchivoPlano) {
        this.nomArchivoPlano = nomArchivoPlano;
    }

    public String getIndTipoRegistro() {
        return indTipoRegistro;
    }

    public void setIndTipoRegistro(String indTipoRegistro) {
        this.indTipoRegistro = indTipoRegistro;
    }

    @Override
    public String toString() {
        return "ArchivoComprobanteRequestDTO{" +
                "numRuc='" + numRuc + '\'' +
                ", arcContenidoPlano='" + arcContenidoPlano + '\'' +
                ", nomArchivoPlano='" + nomArchivoPlano + '\'' +
                ", indTipoRegistro='" + indTipoRegistro + '\'' +
                '}';
    }
}
