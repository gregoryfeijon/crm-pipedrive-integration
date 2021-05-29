package br.com.gregoryfeijon.crmpipedriveintegration.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 27/05/2021 às 21:22:36
 * 
 * @author gregory.feijon
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lead implements Serializable {

	private static final long serialVersionUID = -1850257645513604917L;
	private static final String NOME_OBRIGATORIO = "É necessário especificar o nome do LEAD!";
	private static final String EMAIL_OBRIGATORIO = "É necessário especificar o e-mail do LEAD!";

	private long id;

	@NotBlank(message = NOME_OBRIGATORIO)
	private String nome;

	@Email
	@NotBlank(message = EMAIL_OBRIGATORIO)
	private String email;

	private String empresa;
	private String site;
	private List<String> telefones;
	private Status status = Status.OPEN;
	private String anotacoes;
	private long usuarioResponsavelId;
}
