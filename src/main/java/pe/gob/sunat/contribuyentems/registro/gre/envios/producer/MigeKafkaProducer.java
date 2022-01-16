package pe.gob.sunat.contribuyentems.registro.gre.envios.producer;

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
public interface MigeKafkaProducer {

    void producirMensajeKafka(Object mensajeK);
}
