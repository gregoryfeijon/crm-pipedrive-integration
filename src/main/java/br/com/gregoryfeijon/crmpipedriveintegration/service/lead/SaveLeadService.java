package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.ILeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 25/01/2022 às 22:13
 *
 * @author gregory.feijon
 */

@Service
public class SaveLeadService {

    private static final LoggerUtil LOG = LoggerUtil.getLog(SaveLeadService.class);

    private final ILeadRepository leadRepository;

    public SaveLeadService(ILeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public Optional<Lead> execute(Lead lead) {
        return leadRepository.save(lead);
    }
}
