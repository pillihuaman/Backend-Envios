package pe.gob.sunat.contribuyentems.registro.gre.envios.repository.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.types.ObjectId;

import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.ComprobanteRepository;
import pe.gob.sunat.tecnologia.arquitectura.framework.mongodb.client.MongoDBClient;
import pe.gob.sunat.tecnologia.arquitectura.framework.repository.mongodb.GridFsRepositoryImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ComprobanteRepositoryImpl extends GridFsRepositoryImpl implements ComprobanteRepository {

    private static final String DS_ESCRITURA = "dgbdServMigeArchivos";
    private static final String DS_LECTURA = "dcbdServMigeArchivos";
    private static final String BUCKET = "comprobantes";

    @Override
    public InputStream obtenerArchivoPorNombre(String gridFsFileName) throws IOException {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            GridFSBucket gridFSBucket = MongoDBClient.getGridFSBucket(DS_LECTURA, BUCKET);
            gridFSBucket.downloadToStream(gridFsFileName, baos);
            return new ByteArrayInputStream(baos.toByteArray());
        }

    }

    @Override
    public ObjectId registrarArchivo(InputStream is, String gridFsFileName) throws Exception {
        GridFSBucket gridFSFilesBucket = MongoDBClient.getGridFSBucket(DS_ESCRITURA, BUCKET);
        GridFSUploadOptions options = new GridFSUploadOptions();
        ObjectId fileId = gridFSFilesBucket.uploadFromStream(gridFsFileName, is, options);
        is.close();
        return fileId;
    }
}
