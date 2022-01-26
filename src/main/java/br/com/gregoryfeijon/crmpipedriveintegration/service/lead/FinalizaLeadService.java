package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.dto.LeadFinalizaDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.ILeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 25/01/2022 às 22:19
 *
 * @author gregory.feijon
 */

@Service
public class FinalizaLeadService {

    private static final LoggerUtil LOG = LoggerUtil.getLog(FinalizaLeadService.class);

    private final ILeadRepository leadRepository;

    public FinalizaLeadService(ILeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public Optional<Lead> execute(LeadFinalizaDTO lead) {
        if (lead.getId() == 0) {
            throw new APIException("Não é possível encontrar o lead especificado!");
        }
        return leadRepository.saveLeadStatus(lead);
    }
}
