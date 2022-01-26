package br.com.gregoryfeijon.crmpipedriveintegration.repository.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.dto.LeadFinalizaDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.IRepository;

import java.util.Optional;

/**
 * 25/01/2022 às 14:58
 *
 * @author gregory.feijon
 */

public interface ILeadRepository extends IRepository<Lead> {

    Optional<Lead> saveLeadStatus(Lead lead);
    Optional<Lead> saveLeadStatus(LeadFinalizaDTO leadFinalizaDTO);

}
