package br.com.gregoryfeijon.crmpipedriveintegration.client;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.dto.PipedriveDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.properties.PipedriveProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 29/05/2021 às 18:30:36
 * 
 * @author gregory.feijon
 */

@RestController
public class PipedriveConsumer extends PipedriveAPIClient<PipedriveDTO> {
	
	private final String pipedriveKey;
	
	public PipedriveConsumer(PipedriveProperties pipedriveProperties) {
		this.basePath = pipedriveProperties.getBaseUrl();
		this.pipedriveKey = pipedriveProperties.getKey();
	}

	@Override
	public Optional<PipedriveDTO> sendDeal(PipedriveDTO entity) {
		//NA URL, MONTAR BASEPATH + CAMINHO ESPECIFICO + KEY COMO ARGUMENTO API_TOKEN
		return null;
	}

	@Override
	protected ParameterizedTypeReference<Response<PipedriveDTO>> returnType() {
		return new ParameterizedTypeReference<Response<PipedriveDTO>>() {
		};
	}

}
