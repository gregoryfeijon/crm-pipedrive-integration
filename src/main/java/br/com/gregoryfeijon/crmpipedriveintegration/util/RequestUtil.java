package br.com.gregoryfeijon.crmpipedriveintegration.util;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 26/05/2021 às 23:34:04
 *
 * @author gregory.feijon
 */

@Component
public class RequestUtil<T> {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Método que executa requisição para a URL especificada, obtendo um objeto do
	 * tipo passado no argumento clazz.
	 * 
	 * @param uri   - {@linkplain String}
	 * @param clazz {@linkplain Class}&ltT&gt
	 * @return {@linkplain ResponseEntity}&ltT&gt
	 */
	public ResponseEntity<T> getEntity(String uri, Class<T> clazz) {
		return restTemplate.getForEntity(uri, clazz);
	}

	public ResponseEntity<Response<T>> exchangeAsResponse(String uri, HttpMethod method,
														  ParameterizedTypeReference<Response<T>> responseType) {
		return executeResponse(uri, method, getHttpEntity(), responseType);
	}

	public ResponseEntity<Response<T>> exchangeAsResponse(String uri, HttpMethod method, T entity,
			ParameterizedTypeReference<Response<T>> responseType) {
		return executeResponse(uri, method, entity, responseType);
	}

	private ResponseEntity<Response<T>> executeResponse(String uri, HttpMethod method, T entity,
			ParameterizedTypeReference<Response<T>> responseType) {
		return executeResponse(uri, method, getHttpEntity(entity), responseType);
	}

	private ResponseEntity<Response<T>> executeResponse(String uri, HttpMethod method, HttpEntity<T> httpEntity,
			ParameterizedTypeReference<Response<T>> responseType) {
		return restTemplate.exchange(uri, method, httpEntity, responseType);
	}

	private HttpEntity<T> getHttpEntity() {
		HttpHeaders headers = criaHeaders();
		HttpEntity<T> httpEntity = new HttpEntity<T>(headers);
		return httpEntity;
	}

	private HttpEntity<T> getHttpEntity(T entity) {
		HttpHeaders headers = criaHeaders();
		HttpEntity<T> httpEntity = new HttpEntity<T>(entity, headers);
		return httpEntity;
	}

	private HttpHeaders criaHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return headers;
	}
}
