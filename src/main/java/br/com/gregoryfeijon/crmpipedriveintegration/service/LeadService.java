package br.com.gregoryfeijon.crmpipedriveintegration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gregoryfeijon.crmpipedriveintegration.repository.LeadRepository;

/**
 * 29/05/2021 às 18:52:19
 * 
 * @author gregory.feijon
 */

@Service
public class LeadService {

	@Autowired
	private LeadRepository leadRepository;
}
