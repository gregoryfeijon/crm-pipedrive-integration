package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.lead.ILeadRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 25/01/2022 às 22:09
 *
 * @author gregory.feijon
 */

@Service
public class ListAllLeadsService {

    private static final LoggerUtil LOG = LoggerUtil.getLog(ListAllLeadsService.class);

    private final ILeadRepository leadRepository;

    public ListAllLeadsService(ILeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public List<Lead> listAll() {
        return leadRepository.listAll();
    }
}
