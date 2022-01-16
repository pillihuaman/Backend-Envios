package pe.gob.sunat.contribuyentems.registro.gre.envios.main.config;

import pe.gob.sunat.tecnologia.arquitectura.framework.mongodb.config.MongoDBConfig;
import pe.gob.sunat.tecnologia3.arquitectura.framework.kafka.config.KafkaConsumerConfig;
import pe.gob.sunat.tecnologia3.arquitectura.framework.kafka.config.KafkaProducerConfig;
//import pe.gob.sunat.tecnologia3.arquitectura.framework.kafka.config.KafkaConsumerConfig;
//import pe.gob.sunat.tecnologia3.arquitectura.framework.kafka.config.KafkaProducerConfig;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.config.MicroserviceConfig;

public class EnviosCpeConfig extends MicroserviceConfig {

	private static EnviosCpeConfig config;
	private MongoDBConfig mongodb;
	private KafkaConsumerConfig kafkaConsumer;
	private KafkaProducerConfig kafkaProducer;

	public static EnviosCpeConfig getConfig() {
		return config;
	}

	public void loadConfig() {
		EnviosCpeConfig.config = this;
	}

	public MongoDBConfig getMongodb() {
		return mongodb;
	}

	public void setMongodb(MongoDBConfig mongodb) {
		this.mongodb = mongodb;
	}

	public KafkaConsumerConfig getKafkaConsumer() {
		return kafkaConsumer;
	}
	public void setKafkaConsumer(KafkaConsumerConfig kafkaConsumer) {
		this.kafkaConsumer = kafkaConsumer;
	}
	public KafkaProducerConfig getKafkaProducer() {
		return kafkaProducer;
	}
	public void setKafkaProducer(KafkaProducerConfig kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}
}