package br.com.gregoryfeijon.crmpipedriveintegration.client;

import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RestController;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.dto.PipedriveDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.properties.PipedriveProperties;

/**
 * 29/05/2021 às 18:30:36
 * 
 * @author gregory.feijon
 */

@RestController
public class PipedriveConsumer extends PipedriveAPIClient<PipedriveDTO> {
	
	
	public PipedriveConsumer(PipedriveProperties pipedriveProperties) {
		this.basePath = pipedriveProperties.getBaseUrl();
	}

	@Override
	public Optional<PipedriveDTO> sendDeal(PipedriveDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ParameterizedTypeReference<Response<PipedriveDTO>> returnType() {
		return new ParameterizedTypeReference<Response<PipedriveDTO>>() {
		};
	}

}
