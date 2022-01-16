package pe.gob.sunat.contribuyentems.registro.gre.envios.repository;

import org.bson.Document;

import pe.gob.sunat.contribuyentems.registro.gre.envios.domain.CorrelativosTicket;
import pe.gob.sunat.contribuyentems.registro.gre.envios.domain.EnviosMasivos;

import java.text.ParseException;
import java.time.LocalDate;

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
public interface CorrelativosTicketRepository {

    Integer obtenerValCorrelativo(String numRuc, int anio) throws ParseException;

}
