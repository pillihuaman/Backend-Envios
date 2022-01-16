package pe.gob.sunat.contribuyentems.registro.gre.envios.domain;

import java.io.Serializable;
import java.util.Date;

public class EnviosMasivos implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String numRuc;
    private String codTicketMasivo;
    private String indTipoRegistro;
    private String indEstadoEnvio;
    private String nomArchivoPlano;
    private Date fecInicioProceso;
    private Date fecFinProceso;
    private Integer cntCpe;
    private Integer cntCorrectos;
    private Integer cntErrores;
    private Auditoria auditoria;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumRuc() {
        return numRuc;
    }

    public void setNumRuc(String numRuc) {
        this.numRuc = numRuc;
    }

    public String getCodTicketMasivo() {
        return codTicketMasivo;
    }

    public void setCodTicketMasivo(String codTicketMasivo) {
        this.codTicketMasivo = codTicketMasivo;
    }

    public String getIndTipoRegistro() {
        return indTipoRegistro;
    }

    public void setIndTipoRegistro(String indTipoRegistro) {
        this.indTipoRegistro = indTipoRegistro;
    }

    public String getIndEstadoEnvio() {
        return indEstadoEnvio;
    }

    public void setIndEstadoEnvio(String indEstadoEnvio) {
        this.indEstadoEnvio = indEstadoEnvio;
    }

    public String getNomArchivoPlano() {
        return nomArchivoPlano;
    }

    public void setNomArchivoPlano(String nomArchivoPlano) {
        this.nomArchivoPlano = nomArchivoPlano;
    }

    public Date getFecInicioProceso() {
        return fecInicioProceso;
    }

    public void setFecInicioProceso(Date fecInicioProceso) {
        this.fecInicioProceso = fecInicioProceso;
    }

    public Date getFecFinProceso() {
        return fecFinProceso;
    }

    public void setFecFinProceso(Date fecFinProceso) {
        this.fecFinProceso = fecFinProceso;
    }

    public Integer getCntCpe() {
        return cntCpe;
    }

    public void setCntCpe(Integer cntCpe) {
        this.cntCpe = cntCpe;
    }

    public Integer getCntCorrectos() {
        return cntCorrectos;
    }

    public void setCntCorrectos(Integer cntCorrectos) {
        this.cntCorrectos = cntCorrectos;
    }

    public Integer getCntErrores() {
        return cntErrores;
    }

    public void setCntErrores(Integer cntErrores) {
        this.cntErrores = cntErrores;
    }

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

    
}
