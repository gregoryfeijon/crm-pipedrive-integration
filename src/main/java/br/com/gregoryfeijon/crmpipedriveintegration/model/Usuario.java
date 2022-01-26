package br.com.gregoryfeijon.crmpipedriveintegration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 27/05/2021 às 21:22:42
 * 
 * @author gregory.feijon
 */

@Data
@Builder(setterPrefix = "with")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 8660067838083085498L;

	private long id;

	@NotBlank(message = Constants.NOME_OBRIGATORIO_USUARIO)
	private String nome;

	@Email(message = Constants.EMAIL_INVALIDO_USUARIO)
	@NotBlank(message = Constants.EMAIL_OBRIGATORIO_USUARIO)
	private String email;
}
