package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.ILeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 25/01/2022 às 22:11
 *
 * @author gregory.feijon
 */

@Service
public class FindLeadByEmailService {

    private static final LoggerUtil LOG = LoggerUtil.getLog(FindLeadByEmailService.class);

    private final ILeadRepository leadRepository;

    public FindLeadByEmailService(ILeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public Optional<Lead> findLeadByEmail(Lead lead) {
        return findLeadByEmail(lead.getEmail());
    }

    public Optional<Lead> findLeadByEmail(String email) {
        List<Lead> leadsSalvos = leadRepository.listAll();
        return leadsSalvos.stream().filter(leadSalvo -> Objects.equals(email, leadSalvo.getEmail())).findAny();
    }
}
