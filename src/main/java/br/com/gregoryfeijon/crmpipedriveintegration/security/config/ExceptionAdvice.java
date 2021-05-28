package br.com.gregoryfeijon.crmpipedriveintegration.security.config;

import java.nio.file.AccessDeniedException;

import br.com.gregoryfeijon.crmpipedriveintegration.api.response.Response;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ApiUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 26/05/2021 às 23:47:04
 *
 * @author gregory.feijon
 */

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = {AccessDeniedException.class})
    public <T> ResponseEntity<Response<T>> notAllowed() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiUtil.criarResponseDeErro("Access not allowed"));
    }
}
