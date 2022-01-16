package pe.gob.sunat.contribuyentems.registro.gre.envios.repository.impl;

import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.CorrelativosTicketRepository;
import pe.gob.sunat.tecnologia.arquitectura.framework.mongodb.client.MongoDBClient;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


public class CorrelativosTicketRepositoryImpl implements CorrelativosTicketRepository {

    private static final String COLECCION = "correlativosTicket";
    private static final String DS_ESCRITURA = "dgbdServMige";

    @Override
    public Integer obtenerValCorrelativo(String numRuc, int anio) throws ParseException {

        LinkedHashMap<String, Object> findMap = new LinkedHashMap<>();
        findMap.put("numRuc", numRuc);
        findMap.put("anio", anio);
        Document find = new Document(findMap);
        Document increase = new Document("valCorrelativo", 1);
        Document update = new Document("$inc", increase);
        Document result = MongoDBClient.getCollection(DS_ESCRITURA,
                COLECCION).findOneAndUpdate(find, update, new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        Integer correlativoActual = result.getInteger("valCorrelativo");

        if (correlativoActual.compareTo(1) == 0) {
            MongoDBClient.getCollection(DS_ESCRITURA, COLECCION).updateOne(and(eq("numRuc", numRuc)
                    , eq("anio", anio)), set("fecTicket", new Date()));
        }
        return correlativoActual;

    }

}
