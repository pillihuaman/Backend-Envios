package pe.gob.sunat.contribuyentems.registro.gre.envios.service;

import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.EnviosNoConformidadRequestDTO;
import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.RegEnvioResponseDTO;

public interface EnviosNoConformidadService {

    RegEnvioResponseDTO enviarArchivoNoConformidad(EnviosNoConformidadRequestDTO enviosMasivosRequestDTO) throws Exception;
}
