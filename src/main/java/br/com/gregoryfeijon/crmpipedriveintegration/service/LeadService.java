package br.com.gregoryfeijon.crmpipedriveintegration.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.LeadRepository;

/**
 * 29/05/2021 às 18:52:19
 * 
 * @author gregory.feijon
 */

@Service
public class LeadService implements IService<Lead> {

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
}
