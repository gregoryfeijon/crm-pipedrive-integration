package br.com.gregoryfeijon.crmpipedriveintegration;

import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.properties.QueueProperties;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.ILeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.usuario.IUsuarioRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.service.lead.queue.ConsumerFilaLeads;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Optional;

@SpringBootApplication
@EnableConfigurationProperties
public class CrmPipedriveIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmPipedriveIntegrationApplication.class, args);
	}

	@Bean
	CommandLineRunner initRepos(IUsuarioRepository usuarioRepository, ILeadRepository leadRepository, QueueProperties queueProperties) {
		return args -> {
			criaUsuarioDefault(usuarioRepository);
			limpaLeads(leadRepository);
			iniciaThreadLead(usuarioRepository, leadRepository, queueProperties);
		};
	}

	private void criaUsuarioDefault(IUsuarioRepository usuarioRepository) throws IOException {
		usuarioRepository.deleteAll();
		Usuario usuario = Usuario.builder().withId(1).withEmail("usuario@usuario.usuario.br").withNome("Usuário 1")
				.build();
		Optional<Usuario> opUsuario = usuarioRepository.save(usuario);
		if (!opUsuario.isPresent()) {
			throw new APIException("Não foi possível criar o usuário inicial!");
		}
	}

	private void limpaLeads(ILeadRepository leadRepository) throws IOException {
		leadRepository.deleteAll();
	}

	private void iniciaThreadLead(IUsuarioRepository usuarioRepository, ILeadRepository leadRepository, QueueProperties queueProperties) {
		ConsumerFilaLeads consumer = new ConsumerFilaLeads(usuarioRepository, leadRepository, queueProperties);
		Thread leadQueueManager = new Thread(consumer);
		leadQueueManager.start();
	}
}
