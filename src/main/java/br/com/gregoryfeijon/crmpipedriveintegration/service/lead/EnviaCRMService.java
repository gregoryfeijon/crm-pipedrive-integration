package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import org.springframework.stereotype.Service;

/**
 * 25/01/2022 às 22:21
 *
 * @author gregory.feijon
 */

@Service
public class EnviaCRMService {

    private static final LoggerUtil LOG = LoggerUtil.getLog(EnviaCRMService.class);

    public void execute(Lead leadAux) {
        if (leadAux.getStatus().equals(Status.LOST)) {
            LOG.info("Falta integração!");
        }
    }
}
