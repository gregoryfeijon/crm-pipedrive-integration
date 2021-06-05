package br.com.gregoryfeijon.crmpipedriveintegration.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.gregoryfeijon.crmpipedriveintegration.annotation.RestAPIController;
import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.dto.LeadFinalizaDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import br.com.gregoryfeijon.crmpipedriveintegration.service.LeadService;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ApiUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;

/**
 * 29/05/2021 às 18:52:38
 * 
 * @author gregory.feijon
 */

@RestAPIController("lead")
public class LeadAPIController {

	private static final LoggerUtil LOG = LoggerUtil.getLog(LeadAPIController.class);

	@Autowired
	private LeadService leadService;

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
