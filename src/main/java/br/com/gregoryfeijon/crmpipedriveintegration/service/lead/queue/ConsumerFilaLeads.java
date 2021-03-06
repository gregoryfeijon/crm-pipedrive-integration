package br.com.gregoryfeijon.crmpipedriveintegration.service.lead.queue;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.properties.QueueProperties;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.ILeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.usuario.IUsuarioRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ValidationHelpers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 03/06/2021 às 21:07:24
 * 
 * @author gregory.feijon
 */

public class ConsumerFilaLeads implements Runnable {

	private static final LoggerUtil LOG = LoggerUtil.getLog(ConsumerFilaLeads.class);

	private final IUsuarioRepository usuarioRepository;
	private final ILeadRepository leadRepository;
	private final int THREAD_TIMEOUT;


	@Autowired
	public ConsumerFilaLeads(IUsuarioRepository usuarioRepository, ILeadRepository leadRepository, QueueProperties queueProperties) {
		this.usuarioRepository = usuarioRepository;
		this.leadRepository = leadRepository;
		this.THREAD_TIMEOUT = queueProperties.getLeadQueueTimeout();
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			List<Usuario> usuarios = usuarioRepository.listAll().stream()
					.sorted(Comparator.comparing(Usuario::getId)).collect(Collectors.toList());
			Map<Long, List<Lead>> mapaUsuarioLeads = leadRepository.listAll().stream()
					.filter(l -> l.getUsuarioResponsavelId() != null)
					.collect(Collectors.groupingBy(Lead::getUsuarioResponsavelId));
			if (ValidationHelpers.mapNotEmpty(mapaUsuarioLeads)) {
				Long usuarioId = obtemUsuarioSemLead(usuarios, mapaUsuarioLeads.keySet());
				if (usuarioId == null) {
					usuarioId = verificaUsuarioSemLeadEmAberto(mapaUsuarioLeads);
				}
				atribuiLeadAoUsuario(usuarioId);
				setObjectsToNull(usuarios, usuarioId, mapaUsuarioLeads);
			}
			timeout(THREAD_TIMEOUT);
		}
	}

	private void timeout(int time) {
		try {
			synchronized (this) {
				wait(time);
			}
		} catch (InterruptedException e) {
			LOG.severe("Interrupção inesperada da Thread!");
		}
	}

	private Long obtemUsuarioSemLead(List<Usuario> usuarios, Set<Long> idUsuariosComLead) {
		Long[] usuarioId = { null };
		usuarios.stream().forEach(usuario -> {
			if (usuarioId[0] == null) {
				boolean usuarioSemLead = idUsuariosComLead.stream()
						.noneMatch(idUsuario -> idUsuario == usuario.getId());
				if (usuarioSemLead) {
					usuarioId[0] = usuario.getId();
				}
			}
		});
		return usuarioId[0];
	}

	private Long verificaUsuarioSemLeadEmAberto(Map<Long, List<Lead>> mapaUsuarioLeads) {
		Long[] usuarioId = { null };
		mapaUsuarioLeads.forEach((idUsuario, leads) -> {
			if (usuarioId[0] == null) {
				Optional<Lead> opLeadAberto = leads.stream()
						.filter(leadUsuario -> leadUsuario.getStatus().equals(Status.OPEN)).findAny();
				if (!opLeadAberto.isPresent()) {
					usuarioId[0] = idUsuario;
				}
			}
		});
		return usuarioId[0];
	}

	private void atribuiLeadAoUsuario(Long usuarioId) {
		if (usuarioId != null) {
			Lead nextLead = FilaLeads.getLead();
			nextLead.setUsuarioResponsavelId(usuarioId);
			leadRepository.save(nextLead).ifPresentOrElse(
					lead -> LOG.info("Usuário {0} agora é responsável pelo Lead: {1}", usuarioId, lead),
					() -> LOG.severe("Erro ao atribuir lead a um usuário (através da THREAD!!)"));
		}
	}

	private void setObjectsToNull(List<Usuario> usuarios, Long usuarioId, Map<Long, List<Lead>> mapaUsuarioLeads) {
		usuarios = null;
		usuarioId = null;
		mapaUsuarioLeads = null;
	}
}
