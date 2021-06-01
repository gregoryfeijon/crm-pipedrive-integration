package br.com.gregoryfeijon.crmpipedriveintegration.util;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;

import java.util.Arrays;
import java.util.List;

/**
 * 26/05/2021 às 23:32:04
 *
 * @author gregory.feijon
 */

public final class ApiUtil {

	private static final LoggerUtil LOG = LoggerUtil.getLog(ApiUtil.class);

	private ApiUtil() {}

	public static <T> Response<T> criarResponseDeErro(APIException ex) {
		return criarResponseDeErro(ex.getMessage());
	}

	public static <T> Response<T> criarResponseDeErro(String erro) {
		return criarResponseDeErro(Arrays.asList(erro));
	}

	public static <T> Response<T> criarResponseDeErro(List<String> erros) {
		Response<T> response = new Response<>();
		response.setErrors(erros);
		erros.stream().forEach(erro -> LOG.warning(erro));
		return response;
	}

	public static <T> Response<T> criaResponseBody(T entity) {
		Response<T> response = new Response<>();
		response.setData(entity);
		return response;
	}
}
