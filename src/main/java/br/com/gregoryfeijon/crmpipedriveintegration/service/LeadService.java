package br.com.gregoryfeijon.crmpipedriveintegration.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gregoryfeijon.crmpipedriveintegration.dto.LeadFinalizaDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.LeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ValidationHelpers;

/**
 * 29/05/2021 às 18:52:19
 * 
 * @author gregory.feijon
 */

@Service
public class LeadService implements IService<Lead> {
	
	private static final LoggerUtil LOG = LoggerUtil.getLog(LeadService.class);

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private LeadRepository leadRepository;

	@Override
	public Optional<Lead> save(Lead lead) {
		return leadRepository.salvaLead(lead);
	}

	@Override
	public List<Lead> listAll() {
		return leadRepository.obtemLeads();
	}

	public Optional<Lead> findLeadByEmail(Lead lead) {
		return findLeadByEmail(lead.getEmail());
	}

	public Optional<Lead> findLeadByEmail(String email) {
		List<Lead> leadsSalvos = listAll();
		return leadsSalvos.stream().filter(leadSalvo -> Objects.equals(email, leadSalvo.getEmail())).findAny();
	}

	public Optional<Lead> validaLeadExistente(Lead lead) {
		Optional<Lead> opLead = findLeadByEmail(lead);
		if (opLead.isPresent()) {
			Lead leadSalvo = opLead.get();
			if (leadSalvo.getStatus().equals(Status.OPEN)) {
				return Optional.of(leadSalvo);
			}
			lead.setId(leadSalvo.getId());
			lead.setStatus(Status.OPEN);
		}
		return Optional.empty();
	}

	public void verificaUsuarioDisponivel(Lead lead) {
		List<Usuario> usuarios = usuarioService.listAll().stream().sorted(Comparator.comparing(Usuario::getId))
				.collect(Collectors.toList());
		Map<Long, List<Lead>> mapaUsuarioLeads = listAll().stream().filter(l -> l.getUsuarioResponsavelId() != null)
				.collect(Collectors.groupingBy(Lead::getUsuarioResponsavelId));
		if (ValidationHelpers.mapNotEmpty(mapaUsuarioLeads)) {
			atribuiLeadUsuario(usuarios, mapaUsuarioLeads.keySet(), lead);
			verificaAdicionaLeadFila(mapaUsuarioLeads, lead);
		} else {
			Usuario firstUser = usuarios.stream().findFirst().get();
			lead.setUsuarioResponsavelId(firstUser.getId());
		}
		save(lead);
	}

	private void atribuiLeadUsuario(List<Usuario> usuarios, Set<Long> idUsuariosComLead, Lead lead) {
		usuarios.stream().forEach(usuario -> {
			if (lead.getUsuarioResponsavelId() == null) {
				boolean usuarioSemLead = idUsuariosComLead.stream()
						.noneMatch(usuarioId -> usuarioId == usuario.getId());
				if (usuarioSemLead) {
					lead.setUsuarioResponsavelId(usuario.getId());
				}
			}
		});
	}

	private void verificaAdicionaLeadFila(Map<Long, List<Lead>> mapaUsuarioLeads, Lead lead) {
		mapaUsuarioLeads.forEach((usuarioId, leads) -> {
			if (lead.getUsuarioResponsavelId() == null) {
				Optional<Lead> opLeadAberto = leads.stream()
						.filter(leadUsuario -> leadUsuario.getStatus().equals(Status.OPEN)).findAny();
				if (!opLeadAberto.isPresent()) {
					lead.setUsuarioResponsavelId(usuarioId);
				}
			}
		});
		if (lead.getUsuarioResponsavelId() == null) {
			FilaLeads.addLead(lead);
		}
	}

	public Optional<Lead> finalizaLead(LeadFinalizaDTO lead) {
		if (lead.getId() == 0) {
			throw new APIException("Não é possível encontrar o lead especificado!");
		}
		return leadRepository.salvaStatusLead(lead);
	}
	
	public void enviaLeadCrm(Lead leadAux) {
		if (leadAux.getStatus().equals(Status.LOST)) {
			LOG.info("Falta integração!");
		}
	}
}
