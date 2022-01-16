package pe.gob.sunat.contribuyentems.servicio.controlcpe.registro.envios.service.impl;

import com.mongodb.MongoGridFSException;
import com.mongodb.MongoTimeoutException;
import org.bson.types.ObjectId;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import pe.gob.sunat.contribuyentems.registro.gre.envios.producer.MigeKafkaProducer;
import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.ComprobanteRepository;
import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.CorrelativosTicketRepository;
import pe.gob.sunat.contribuyentems.registro.gre.envios.repository.EnviosNoConformidadRepository;
import pe.gob.sunat.contribuyentems.registro.gre.envios.service.impl.EnviosNoConformidadServiceImpl;
import pe.gob.sunat.contribuyentems.registro.gre.envios.util.Util;
import pe.gob.sunat.contribuyentems.registro.gre.envios.util.exceptions.UnprocessableEntityException;
import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.EnviosNoConformidadRequestDTO;
import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.dto.RegEnvioResponseDTO;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.util.UtilLog;

import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Util.class)
public class ComprobanteServiceImplTest {

    @InjectMocks
    private EnviosNoConformidadServiceImpl comprobanteService;

    @Mock
    private ComprobanteRepository comprobanteRepository;

    @Mock
    private EnviosNoConformidadRepository enviosMasivosRepository;

    @Mock
    private CorrelativosTicketRepository correlativosTicketRepository;

    @Mock
    private MigeKafkaProducer kafkaProducer;

    @Mock
    private UtilLog utilLog;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void cuando_ya_existe_el_archivo_en_bd() throws Exception {
        //given
        EnviosNoConformidadRequestDTO requestDTO = new EnviosNoConformidadRequestDTO();
        FormDataContentDisposition formDataContentDisposition = Whitebox.newInstance(FormDataContentDisposition.class);
        Whitebox.setInternalState(formDataContentDisposition, "fileName", "99999999999-TTT-AAAAMMDD-99.txt");
        requestDTO.setArchivoDetalle(formDataContentDisposition);
        requestDTO.setArchivoDetalle(formDataContentDisposition);
        FileInputStream fileInputStream = Whitebox.newInstance(FileInputStream.class);

        //when
        Mockito.when(comprobanteRepository.obtenerArchivoPorNombre(anyString())).thenReturn(fileInputStream);

        //then
        RegEnvioResponseDTO responseDTO = comprobanteService.enviarArchivoComprobante(requestDTO);

        Assert.assertEquals("02",responseDTO.getCodOperacion());
        Assert.assertEquals("El archivo 99999999999-TTT-AAAAMMDD-99.txt fue previamente enviado",responseDTO.getDesRespuesta());

    }
    @Test(expected = IOException.class)
    public void cuando_hay_exception_en_llamada_bd() throws Exception {

        //given
        EnviosNoConformidadRequestDTO requestDTO = new EnviosNoConformidadRequestDTO();
        FormDataContentDisposition formDataContentDisposition = Whitebox.newInstance(FormDataContentDisposition.class);
        Whitebox.setInternalState(formDataContentDisposition, "fileName", "99999999999-TTT-AAAAMMDD-99.txt");
        requestDTO.setArchivoDetalle(formDataContentDisposition);
        requestDTO.setArchivoDetalle(formDataContentDisposition);

        //when
        Mockito.when(comprobanteRepository.obtenerArchivoPorNombre(anyString())).thenThrow(new IOException());

        //then
        comprobanteService.enviarArchivoComprobante(requestDTO);

    }
    @Test(expected = UnprocessableEntityException.class)
    public void cuando_hay_exception_en_llamada_bd_MongoGridFSException() throws Exception {

        //given
        EnviosNoConformidadRequestDTO requestDTO = new EnviosNoConformidadRequestDTO();
        FormDataContentDisposition formDataContentDisposition = Whitebox.newInstance(FormDataContentDisposition.class);
        Whitebox.setInternalState(formDataContentDisposition, "fileName", "99999999999-TTT-AAAAMMDD-99.txt");
        requestDTO.setArchivoDetalle(formDataContentDisposition);
        requestDTO.setArchivoDetalle(formDataContentDisposition);

        //when
        Mockito.when(comprobanteRepository.obtenerArchivoPorNombre(anyString())).thenThrow(new MongoGridFSException("No file found"));

        //then
        comprobanteService.enviarArchivoComprobante(requestDTO);

    }
    @Test(expected = UnprocessableEntityException.class)
    public void cuando_hay_exception_en_llamada_bd_MongoTimeoutException() throws Exception {

        //given
        EnviosNoConformidadRequestDTO requestDTO = new EnviosNoConformidadRequestDTO();
        FormDataContentDisposition formDataContentDisposition = Whitebox.newInstance(FormDataContentDisposition.class);
        Whitebox.setInternalState(formDataContentDisposition, "fileName", "99999999999-TTT-AAAAMMDD-99.txt");
        requestDTO.setArchivoDetalle(formDataContentDisposition);
        requestDTO.setArchivoDetalle(formDataContentDisposition);

        //when
        Mockito.when(comprobanteRepository.obtenerArchivoPorNombre(anyString())).thenThrow(new MongoTimeoutException("Timed out"));

        //then
        comprobanteService.enviarArchivoComprobante(requestDTO);

    }
    @Test
    public void cuando_registrar_archivo() throws Exception {
        PowerMockito.mockStatic(Util.class);
        //given
        EnviosNoConformidadRequestDTO requestDTO = new EnviosNoConformidadRequestDTO();
        requestDTO.setNumRuc("");

        FormDataContentDisposition formDataContentDisposition = Whitebox.newInstance(FormDataContentDisposition.class);
        Whitebox.setInternalState(formDataContentDisposition, "fileName", "99999999999-TTT-AAAAMMDD-99.txt");
        requestDTO.setArchivoDetalle(formDataContentDisposition);
        requestDTO.setArchivoDetalle(formDataContentDisposition);

        //when
        Mockito.when(Util.obtenerAnioActual()).thenReturn(2020);
        Mockito.when(comprobanteRepository.obtenerArchivoPorNombre(anyString())).thenReturn(null);
        Mockito.when(comprobanteRepository.registrarArchivo(any(),anyString())).thenReturn(new ObjectId());

        //then
        comprobanteService.enviarArchivoComprobante(requestDTO);
        Mockito.verify(kafkaProducer).producirMensajeKafka(any());
    }
}