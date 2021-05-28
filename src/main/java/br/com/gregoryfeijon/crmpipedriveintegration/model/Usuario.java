package br.com.gregoryfeijon.crmpipedriveintegration.model;

import java.io.Serializable;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
27/05/2021 às 21:22:42

@author gregory.feijon
*/

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 8660067838083085498L;

	private long id;
	private String nome;
	
	@Email
	private String email;
}
