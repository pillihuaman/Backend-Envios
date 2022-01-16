package pe.gob.sunat.contribuyentems.registro.gre.envios.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CorrelativosTicket {

    private String id;
    private String numRuc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
    private Date fecTicket;

    private Integer valCorrelativo;
    private String anio;
    private Auditoria auditoria;


    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getId() {
        return id;
    }

    public CorrelativosTicket setId(String id) {
        this.id = id;
        return this;
    }

    public String getNumRuc() {
        return numRuc;
    }

    public CorrelativosTicket setNumRuc(String numRuc) {
        this.numRuc = numRuc;
        return this;
    }

    public Date getFecTicket() {
        return fecTicket;
    }

    public CorrelativosTicket setFecTicket(Date fecTicket) {
        this.fecTicket = fecTicket;
        return this;
    }

    public Integer getValCorrelativo() {
        return valCorrelativo;
    }

    public CorrelativosTicket setValCorrelativo(Integer valCorrelativo) {
        this.valCorrelativo = valCorrelativo;
        return this;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }
}
