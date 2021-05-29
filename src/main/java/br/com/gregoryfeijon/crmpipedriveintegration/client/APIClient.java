package br.com.gregoryfeijon.crmpipedriveintegration.client;

import org.springframework.core.ParameterizedTypeReference;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.util.RequestUtil;

/**
29/05/2021 às 16:30:33

@author gregory.feijon
*/

public abstract class APIClient<T> {

	protected RequestUtil<T> requestUtil;
	
	protected String basePath;
	
	protected abstract ParameterizedTypeReference<Response<T>> returnType();
}
