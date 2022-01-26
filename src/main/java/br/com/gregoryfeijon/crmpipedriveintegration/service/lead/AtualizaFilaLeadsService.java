package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.service.lead.queue.FilaLeads;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 25/01/2022 às 22:47
 *
 * @author gregory.feijon
 */

@Service
public class AtualizaFilaLeadsService {

    public void execute(Map<Long, List<Lead>> mapaUsuarioLeads, Lead lead) {
        mapaUsuarioLeads.forEach((usuarioId, leads) -> {
            if (lead.getUsuarioResponsavelId() == null) {
                Optional<Lead> opLeadAberto = leads.stream()
                        .filter(leadUsuario -> leadUsuario.getStatus().equals(Status.OPEN)).findAny();
                if (!opLeadAberto.isPresent()) {
                    lead.setUsuarioResponsavelId(usuarioId);
                }
            }
        });
        if (lead.getUsuarioResponsavelId() == null) {
            FilaLeads.addLead(lead);
        }
    }
}
