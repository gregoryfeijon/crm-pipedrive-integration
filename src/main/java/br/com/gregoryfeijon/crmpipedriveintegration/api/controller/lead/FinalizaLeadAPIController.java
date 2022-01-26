package br.com.gregoryfeijon.crmpipedriveintegration.api.controller.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.annotation.RestAPIController;
import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.dto.LeadFinalizaDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.service.lead.EnviaCRMService;
import br.com.gregoryfeijon.crmpipedriveintegration.service.lead.FinalizaLeadService;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ApiUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RestAPIController("lead")
public class FinalizaLeadAPIController {

    private static final LoggerUtil LOG = LoggerUtil.getLog(FinalizaLeadAPIController.class);

    @Autowired
    private FinalizaLeadService finalizaLeadService;

    @Autowired
    private EnviaCRMService enviaCRMService;

    @PutMapping("/finaliza")
    public ResponseEntity<Response<Lead>> finalizaLead(@Validated @RequestBody LeadFinalizaDTO lead) {
        LOG.info("Finalizando Lead: {0}", lead);
        if (!lead.getStatus().equals(Status.OPEN)) {
            Optional<Lead> opLeadAlterado = finalizaLeadService.execute(lead);
            if (!opLeadAlterado.isPresent()) {
                return ResponseEntity.badRequest()
                        .body(ApiUtil.criarResponseDeErro("Não foi possível finalizar o lead!"));
            }
            Lead leadAux = opLeadAlterado.get();
            enviaCRMService.execute(leadAux);
            return ResponseEntity.ok(ApiUtil.criaResponseBody(leadAux));
        }
        return ResponseEntity.badRequest()
                .body(ApiUtil.criarResponseDeErro("O lead só pode ser finalizado com os status WON ou LOST!"));
    }
}
