package br.com.gregoryfeijon.crmpipedriveintegration.dto;

import javax.validation.constraints.NotNull;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Status;
import lombok.Getter;
import lombok.Setter;

import lombok.ToString;

/**
04/06/2021 às 22:51:35

@author gregory.feijon
*/

@Getter
@Setter
@ToString
public class LeadFinalizaDTO {

	private long id;
	
	@NotNull
	private Status status;
}
