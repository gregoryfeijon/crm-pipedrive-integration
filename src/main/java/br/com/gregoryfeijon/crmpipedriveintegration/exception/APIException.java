package br.com.gregoryfeijon.crmpipedriveintegration.exception;

import java.util.List;

/**
 * 
 * 31/05/2021 às 20:00:33
 * 
 * @author gregory.feijon
 * 
 */

public class APIException extends RuntimeException {

	private static final long serialVersionUID = -373125608135393328L;

	public APIException(String message) {
		super(message);
	}

	public APIException(List<String> messages) {
		super(montaMensagemErro(messages));
	}

	public APIException(Throwable ex) {
		super(ex);
	}

	public APIException(String message, Throwable ex) {
		super(message, ex);
	}

	protected static String montaMensagemErro(List<String> errors) {
		StringBuilder sb = new StringBuilder();
		errors.stream().forEach(error -> {
			if (error.contains(":")) {
				sb.append(error).append("\n\n");
			} else {
				sb.append(error).append("\n");
			}
		});
		return sb.toString();
	}
}