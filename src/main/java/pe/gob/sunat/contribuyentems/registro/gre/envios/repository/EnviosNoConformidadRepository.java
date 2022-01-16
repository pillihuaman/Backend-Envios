package pe.gob.sunat.contribuyentems.registro.gre.envios.repository;

import pe.gob.sunat.contribuyentems.registro.gre.envios.domain.EnviosMasivos;


public interface EnviosNoConformidadRepository {

    String guardarEnviosMasivos(EnviosMasivos enviosMasivos);
}
