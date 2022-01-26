package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 25/01/2022 às 22:24
 *
 * @author gregory.feijon
 */

@Service
public class ValidaLeadExistenteService {

    private static final LoggerUtil LOG = LoggerUtil.getLog(ValidaLeadExistenteService.class);

    @Autowired
    private FindLeadByEmailService findLeadByEmailService;

    public Optional<Lead> execute(Lead lead) {
        Optional<Lead> opLead = findLeadByEmailService.findLeadByEmail(lead);
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
}
