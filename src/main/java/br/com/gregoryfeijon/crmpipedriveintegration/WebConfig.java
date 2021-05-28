package br.com.gregoryfeijon.crmpipedriveintegration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.gregoryfeijon.crmpipedriveintegration.annotation.RestAPIController;

/**
 * 27/05/2021 às 21:12:00
 * 
 * @author gregory.feijon
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestAPIController.class));
	}
}
