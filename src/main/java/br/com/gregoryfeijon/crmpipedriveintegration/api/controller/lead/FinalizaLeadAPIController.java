package br.com.gregoryfeijon.crmpipedriveintegration.api.controller.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.dto.LeadFinalizaDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ApiUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public class FinalizaLeadAPIController extends LeadAPIController {
    @PutMapping("/finaliza")
    public ResponseEntity<Response<Lead>> finalizaLead(@Validated @RequestBody LeadFinalizaDTO lead) {
        LOG.info("Finalizando Lead: {0}", lead);
        if (!lead.getStatus().equals(Status.OPEN)) {
            Optional<Lead> opLeadAlterado = leadService.finalizaLead(lead);
            if (!opLeadAlterado.isPresent()) {
                return ResponseEntity.badRequest()
                        .body(ApiUtil.criarResponseDeErro("Não foi possível finalizar o lead!"));
            }
            Lead leadAux = opLeadAlterado.get();
            leadService.enviaLeadCrm(leadAux);
            return ResponseEntity.ok(ApiUtil.criaResponseBody(leadAux));
        }
        return ResponseEntity.badRequest()
                .body(ApiUtil.criarResponseDeErro("O lead só pode ser finalizado com os status WON ou LOST!"));
    }
}
