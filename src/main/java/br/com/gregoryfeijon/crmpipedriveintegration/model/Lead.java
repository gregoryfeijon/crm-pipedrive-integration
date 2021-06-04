package br.com.gregoryfeijon.crmpipedriveintegration.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	private long id;

	@NotBlank(message = Constants.NOME_OBRIGATORIO_LEAD)
	private String nome;

	@Email
	@NotBlank(message = Constants.EMAIL_OBRIGATORIO_LEAD)
	private String email;

	private String empresa;
	private String site;
	private List<String> telefones;

	@NotNull(message = Constants.STATUS_OBRIGATORIO_LEAD)
	private Status status = Status.OPEN;
	private String anotacoes;
	private Long usuarioResponsavelId;
}
