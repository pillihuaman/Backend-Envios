package pe.gob.sunat.contribuyentems.registro.gre.envios.main;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import pe.gob.sunat.contribuyentems.registro.gre.envios.main.config.EnviosCpeConfig;
import pe.gob.sunat.contribuyentems.registro.gre.envios.ws.rest.ComprobanteNoConformidadRestService;
import pe.gob.sunat.contribuyentems.registro.gre.shared.utils.exceptions.UnprocessableEntityExceptionMapper;
import pe.gob.sunat.contribuyentems.registro.gre.shared.utils.exceptions.WebApplicationExceptionMapper;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.SunatApplication;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.exception.ConstraintViolationExceptionMapper;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.exception.GenericExceptionMapper;
import pe.gob.sunat.tecnologiams.arquitectura.framework.microservices.exception.ObjectNotFoundExceptionMapper;

public class EnviosNoConformidadApplication extends SunatApplication<EnviosCpeConfig> {

	@Override
	public void onRun(EnviosCpeConfig myConfig, Environment environment) throws Exception {
		
		environment.jersey().register(WebApplicationExceptionMapper.class);
		environment.jersey().register(GenericExceptionMapper.class);
		environment.jersey().register(UnprocessableEntityExceptionMapper.class);
		environment.jersey().register(MultiPartFeature.class);
		environment.jersey().register(ComprobanteNoConformidadRestService.class);
		   environment.jersey().register(ConstraintViolationExceptionMapper.class);
		    environment.jersey().register(ObjectNotFoundExceptionMapper.class);
		    environment.jersey().register(GenericExceptionMapper.class);
		myConfig.loadConfig();
		myConfig.getMongodb().loadConfig();

	}

	@Override
	public void onInitialize(Bootstrap<EnviosCpeConfig> bootstrap) {
		// Empty implementation for default
		bootstrap.addBundle(new ViewBundle<EnviosCpeConfig>());
	}

	public static void main(String... params) throws Exception {
		new EnviosNoConformidadApplication().run(params);
	}

}
