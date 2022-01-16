package pe.gob.sunat.contribuyentems.registro.gre.envios.repository;

import org.bson.types.ObjectId;
import pe.gob.sunat.tecnologia.arquitectura.framework.repository.GridFsRepository;

import java.io.IOException;
import java.io.InputStream;

/**
 * Proyecto MIGE
 * MIGE Microservicios
 * Para: SUNAT
 * Canvia
 * Microservicio: Conculta comprobante
 *
 * @author Anthony Pinero
 * @version 1.0
 * @email: apineror@canvia.com
 * @since 19/11/20
 */
public interface ComprobanteRepository extends GridFsRepository {

    InputStream obtenerArchivoPorNombre(String gridFsFileName) throws IOException;

    ObjectId registrarArchivo(InputStream is, String gridFsFileName) throws Exception;
}
