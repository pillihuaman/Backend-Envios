package pe.gob.sunat.contribuyentems.registro.gre.envios.repository.impl;

import org.bson.conversions.Bson;

import pe.gob.sunat.tecnologia.arquitectura.framework.mongodb.client.MongoDBClient;

public abstract class MongoDBRepository {

	private static final String DS_LECTURA = "dcbdServMige";
	private static final String DS_ESCRITURA = "dgbdServMige";

	/*
	public <T> MongoCollection<T> getCollection(String colname, Class<T> t) {

		MongoCollection<T> collection = MongoDBClient.getCollection(DS_LECTURA, colname, t);
		return collection;
	}

	
	public <T> MongoCollection<T> insertCollection(String colname, Class<T> t, List<T> l) {

		MongoCollection<T> collection = MongoDBClient.getCollection(DS_ESCRITURA, colname, t);
		collection.insertMany(l);

		collection = MongoDBClient.getCollection(DS_ESCRITURA, colname, t);
		return collection;
	}
	*/
	public <T> T findOne(String collectionName, Class<T> className, Bson objFilter) throws Exception {

		return MongoDBClient.getCollection(DS_LECTURA, collectionName, className).find(objFilter).first();
	}

	
}

