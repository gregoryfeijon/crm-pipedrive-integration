package br.com.gregoryfeijon.crmpipedriveintegration.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.LeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.UsuarioRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;

/**
 * 03/06/2021 às 21:07:24
 * 
 * @author gregory.feijon
 */

public class ConsumerFilaLeads implements Runnable {

	private static final LoggerUtil LOG = LoggerUtil.getLog(ConsumerFilaLeads.class);

	private final UsuarioRepository usuarioRepository;
	private final LeadRepository leadRepository;

	public ConsumerFilaLeads(UsuarioRepository usuarioRepository, LeadRepository leadRepository) {
		this.usuarioRepository = usuarioRepository;
		this.leadRepository = leadRepository;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			List<Usuario> usuarios = usuarioRepository.obtemUsuarios().stream()
					.sorted(Comparator.comparing(Usuario::getId)).collect(Collectors.toList());
			Map<Long, List<Lead>> mapaUsuarioLeads = leadRepository.obtemLeads().stream()
					.filter(l -> l.getUsuarioResponsavelId() != null)
					.collect(Collectors.groupingBy(Lead::getUsuarioResponsavelId));
			Long usuarioId = obtemUsuarioSemLead(usuarios, mapaUsuarioLeads.keySet());
			if (usuarioId == null) {
				verificaUsuarioSemLeadEmAberto(mapaUsuarioLeads);
			}
			atribuiLeadAoUsuario(usuarioId);
			setObjectsToNull(usuarios, usuarioId, mapaUsuarioLeads);
			try {
				wait(1000);
			} catch (InterruptedException e) {
				LOG.severe("Interrupção inesperada da Thread!");
			}
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
			Optional<Lead> opLeadSalvo = leadRepository.salvaLead(nextLead);
			if (!opLeadSalvo.isPresent()) {
				LOG.severe("Erro ao atribuir lead a um usuário (através da THREAD!!)");
			}
		}
	}

	private void setObjectsToNull(List<Usuario> usuarios, Long usuarioId, Map<Long, List<Lead>> mapaUsuarioLeads) {
		usuarios = null;
		usuarioId = null;
		mapaUsuarioLeads = null;
	}
}
