package pe.gob.sunat.contribuyentems.registro.gre.envios.repository.impl;

import com.mongodb.client.MongoCollection;

import pe.gob.sunat.contribuyentems.registro.gre.envios.domain.EnviosMasivos;
import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.EnviosNoConformidadRepository;
import pe.gob.sunat.tecnologia.arquitectura.framework.mongodb.client.MongoDBClient;

public class EnviosNoConformidadRepositoryImpl extends MongoDBRepository  implements EnviosNoConformidadRepository {

    private static final String COLECCION = "enviosMasivos";
    private static final String DS_ESCRITURA = "dgbdServMige";

    @Override
    public String guardarEnviosMasivos(EnviosMasivos enviosMasivos) {

            MongoCollection<EnviosMasivos> collection = MongoDBClient.getCollection(DS_ESCRITURA, COLECCION, EnviosMasivos.class);
            collection.insertOne(enviosMasivos);

        return "guardado";
    }

}
