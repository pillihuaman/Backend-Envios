package pe.gob.sunat.contribuyentems.registro.gre.envios.util;

import java.util.ArrayList;
import java.util.List;

import pe.gob.sunat.contribuyentems.registro.gre.shared.utils.exceptions.ErrorMessage;


public class ValidatorParams {

	boolean error = false;

	public List<ErrorMessage> lstErrorMsg = new ArrayList<ErrorMessage>();

	public boolean existErrors() {
		return error;
	}

	public void addErrors(ErrorMessage errorMsg) {
		error = true;
		lstErrorMsg.add(errorMsg);
	}
	
	public List<ErrorMessage> getlstErrorMsg() {
		return lstErrorMsg;
	}

}
