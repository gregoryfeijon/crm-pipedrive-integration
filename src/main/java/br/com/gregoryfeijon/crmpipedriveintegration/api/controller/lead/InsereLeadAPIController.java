package br.com.gregoryfeijon.crmpipedriveintegration.api.controller.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ApiUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public class InsereLeadAPIController extends LeadAPIController {

    /**
     * <strong>URL</strong>: http://localhost:8080/api/lead<br>
     * <strong>Method</strong>: POST
     *
     * @param {@linkplain Lead} lead
     * @return {@linkplain ResponseEntity}&lt{@linkplain Response}&lt{@linkplain Lead}&gt&gt
     */
    @PostMapping
    public ResponseEntity<Response<Lead>> insereLead(@Validated @RequestBody Lead lead) {
        LOG.info("Inserindo Lead: {0}", lead);
        Optional<Lead> opLead = leadService.validaLeadExistente(lead);
        if (opLead.isPresent()) {
            return ResponseEntity.ok(ApiUtil.criaResponseBody(lead));
        }
        Optional<Lead> opLeadSalvo = leadService.save(lead);
        if (!opLeadSalvo.isPresent()) {
            return ResponseEntity.badRequest().body(ApiUtil.criarResponseDeErro("Erro ao salvar lead."));
        }
        leadService.verificaUsuarioDisponivel(lead);
        return ResponseEntity.ok(ApiUtil.criaResponseBody(opLeadSalvo.get()));
    }
}
