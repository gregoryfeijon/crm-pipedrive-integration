package br.com.gregoryfeijon.crmpipedriveintegration.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
29/05/2021 às 18:27:33

@author gregory.feijon
*/

@Component
public class CRMConsumer {

	@Autowired
	private PipedriveConsumer pipedriveConsumer;
	
}
