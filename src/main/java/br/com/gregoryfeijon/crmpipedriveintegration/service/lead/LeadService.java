package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.ILeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.service.lead.queue.FilaLeads;
import br.com.gregoryfeijon.crmpipedriveintegration.service.usuario.ListAllUsuariosService;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ValidationHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 29/05/2021 às 18:52:19
 * 
 * @author gregory.feijon
 */

@Service
public abstract class LeadService {
	
	private static final LoggerUtil LOG = LoggerUtil.getLog(LeadService.class);

	protected final ListAllUsuariosService listUsuarioService;
	protected final ILeadRepository leadRepository;

	@Autowired
	public LeadService(ListAllUsuariosService listUsuarioService, ILeadRepository leadRepository) {
		this.listUsuarioService = listUsuarioService;
		this.leadRepository = leadRepository;
	}
}
