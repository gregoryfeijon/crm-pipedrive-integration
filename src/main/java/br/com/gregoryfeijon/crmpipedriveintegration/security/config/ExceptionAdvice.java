package br.com.gregoryfeijon.crmpipedriveintegration.security.config;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ApiUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * 26/05/2021 às 23:47:04
 *
 * @author gregory.feijon
 */

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { AccessDeniedException.class })
	public <T> ResponseEntity<Response<T>> notAllowed() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiUtil.criarResponseDeErro("Access not allowed"));
	}

	@ExceptionHandler(APIException.class)
	public final <T> ResponseEntity<Response<T>> handleInternalException(APIException ex) {
		return ResponseEntity.badRequest().body(ApiUtil.criarResponseDeErro(ex));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> errors = ex.getBindingResult().getFieldErrors();
		StringBuilder sb = new StringBuilder("Erros de validação dos dados inseridos:\n");
		errors.stream().forEach(erro -> sb.append(erro.getDefaultMessage()));
		return ResponseEntity.badRequest().body(ApiUtil.criarResponseDeErro(sb.toString()));
	}
}
