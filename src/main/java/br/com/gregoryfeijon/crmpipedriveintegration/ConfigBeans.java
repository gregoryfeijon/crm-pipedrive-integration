package br.com.gregoryfeijon.crmpipedriveintegration;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.properties.PipedriveProperties;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.ILeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.LeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.usuario.IUsuarioRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.usuario.UsuarioRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.security.properties.JwtProperties;
import br.com.gregoryfeijon.crmpipedriveintegration.util.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 27/05/2021 às 00:07
 *
 * @author gregory.feijon
 */

@Configuration
public class ConfigBeans {

	@Autowired
	private RestTemplateResponseErrorHandler errorHandler;

	/**
	 * Bean de configuração do RestTemplate, para definir a forma que os erros serão
	 * tratados, bem como modificar o RequestFactory, para poder trabalhar de forma
	 * mais livre com a ResponseEntity.
	 *
	 * @param builder - {@linkplain RestTemplateBuilder}
	 * @return {@linkplain RestTemplate}
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.errorHandler(errorHandler).build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		return restTemplate;
	}

	@Bean
	@ConfigurationProperties(prefix = "jwt-prop")
	public JwtProperties jwtProperties() {
		return new JwtProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "pipedrive-prop")
	public PipedriveProperties pipedriveProperties() {
		return new PipedriveProperties();
	}

	@Bean
	public IUsuarioRepository usuarioRepository() {
		return new UsuarioRepository();
	}

	@Bean
	public ILeadRepository iLeadRepository() {
		return new LeadRepository();
	}
}
