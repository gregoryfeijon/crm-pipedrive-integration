package br.com.gregoryfeijon.crmpipedriveintegration.api.controller.lead;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.gregoryfeijon.crmpipedriveintegration.annotation.RestAPIController;
import br.com.gregoryfeijon.crmpipedriveintegration.service.lead.LeadService;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;

/**
 * 29/05/2021 às 18:52:38
 * 
 * @author gregory.feijon
 */

@RestAPIController("lead")
public abstract class LeadAPIController {

	private static final LoggerUtil LOG = LoggerUtil.getLog(LeadAPIController.class);

	@Autowired
	protected LeadService leadService;
}
