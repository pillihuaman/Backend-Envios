package pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto;


import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public class EnviosNoConformidadRequestDTO {

    private InputStream archivoCargado;
    private FormDataContentDisposition archivoDetalle;
    private String numRuc;
    private String inTipoRegistro;
	private long tamanioArchivo;
    public long getTamanioArchivo() {
		return tamanioArchivo;
	}

	public void setTamanioArchivo(long tamañoArchivo) {
		this.tamanioArchivo = tamañoArchivo;
	}
    public InputStream getArchivoCargado() {
        return archivoCargado;
    }

    public void setArchivoCargado(InputStream archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    public FormDataContentDisposition getArchivoDetalle() {
        return archivoDetalle;
    }

    public void setArchivoDetalle(FormDataContentDisposition archivoDetalle) {
        this.archivoDetalle = archivoDetalle;
    }

    public String getNumRuc() {
        return numRuc;
    }

    public void setNumRuc(String numRuc) {
        this.numRuc = numRuc;
    }

    public String getInTipoRegistro() {
        return inTipoRegistro;
    }

    public void setInTipoRegistro(String inTipoRegistro) {
        this.inTipoRegistro = inTipoRegistro;
    }
}
