package br.com.gregoryfeijon.crmpipedriveintegration.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

/**
 * 27/05/2021 às 21:22:42
 * 
 * @author gregory.feijon
 */

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 8660067838083085498L;
	private static final String NOME_OBRIGATORIO = "É necessário especificar o nome do USUÁRIO!";
	private static final String EMAIL_OBRIGATORIO = "É necessário especificar o e-mail do USUÁRIO!";

	private long id;

	@NotBlank(message = NOME_OBRIGATORIO)
	private String nome;

	@Email
	@NotBlank(message = EMAIL_OBRIGATORIO)
	private String email;
}
