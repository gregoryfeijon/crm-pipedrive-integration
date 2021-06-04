package br.com.gregoryfeijon.crmpipedriveintegration;

import java.io.IOException;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.LeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.UsuarioRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.service.ConsumerFilaLeads;

@SpringBootApplication
@EnableConfigurationProperties
public class CrmPipedriveIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmPipedriveIntegrationApplication.class, args);
	}

	@Bean
	CommandLineRunner initRepos(UsuarioRepository usuarioRepository, LeadRepository leadRepository) {
		return args -> {
			criaUsuarioDefault(usuarioRepository);
			limpaLeads(leadRepository);
			iniciaThreadLead(usuarioRepository, leadRepository);
		};
	}

	private void criaUsuarioDefault(UsuarioRepository usuarioRepository) throws IOException {
		usuarioRepository.limpaUsuarios();
		Usuario usuario = Usuario.builder().withId(1).withEmail("usuario@usuario.usuario.br").withNome("Usuário 1")
				.build();
		Optional<Usuario> opUsuario = usuarioRepository.salvaUsuario(usuario);
		if (!opUsuario.isPresent()) {
			throw new APIException("Não foi possível criar o usuário inicial!");
		}
	}

	private void limpaLeads(LeadRepository leadRepository) throws IOException {
		leadRepository.limpaLeads();
	}

	private void iniciaThreadLead(UsuarioRepository usuarioRepository, LeadRepository leadRepository) {
		ConsumerFilaLeads consumer = new ConsumerFilaLeads(usuarioRepository, leadRepository);
		Thread leadQueueManager = new Thread(consumer);
		leadQueueManager.start();
	}
}
