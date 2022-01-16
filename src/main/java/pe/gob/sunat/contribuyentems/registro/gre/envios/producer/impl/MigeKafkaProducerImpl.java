package pe.gob.sunat.contribuyentems.registro.gre.envios.producer.impl;

import pe.gob.sunat.contribuyentems.registro.gre.envios.main.config.EnviosCpeConfig;
import pe.gob.sunat.contribuyentems.registro.gre.envios.producer.MigeKafkaProducer;
import pe.gob.sunat.tecnologia3.arquitectura.framework.kafka.Producer;


public class MigeKafkaProducerImpl implements MigeKafkaProducer {

    @Override
    public void producirMensajeKafka(Object mensaje) {
        Producer producer = new Producer(EnviosCpeConfig.getConfig().getKafkaProducer());
        producer.send(mensaje);
    }

}