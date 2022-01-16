package pe.gob.sunat.contribuyentems.registro.gre.envios.util.exceptions;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = 8598092070778240763L;

	private boolean success;
	private String codError;
	private String desError;

	public ErrorMessage(boolean success, String codError, String desError) {
		this.success = success;
		this.codError = codError;
		this.desError = desError;
	}

	public ErrorMessage(String codError, String desError) {
		this.codError = codError;
		this.desError = desError;
	}

	public ErrorMessage(HTTPErrorEnum httpErrorEnum) {
		this.codError = String.valueOf(httpErrorEnum.getCode());
		this.desError = httpErrorEnum.getMsg();
	}

	public ErrorMessage(ErrorEnum unprocessableErrorEnum) {
		this.codError = String.valueOf(unprocessableErrorEnum.getCode());
		this.desError = unprocessableErrorEnum.getMsg();
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCodError() {
		return codError;
	}

	public void setCodError(String codError) {
		this.codError = codError;
	}

	public String getDesError() {
		return desError;
	}

	public void setDesError(String desError) {
		this.desError = desError;
	}

	@Override
	public String toString() {
		return "ErrorMessage [codError=" + getCodError() + ", desError=" + getDesError() + "]";
	}

}
