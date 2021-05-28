package br.com.gregoryfeijon.crmpipedriveintegration.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;

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
	private String nome;

	@Email
	private String email;
	private String Empresa;

	private String site;
	private List<String> telefones;
	private Status status = Status.OPEN;
	private String anotações;
	private Usuario usuarioResponsavel;
}
