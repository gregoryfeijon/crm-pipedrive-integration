package br.com.gregoryfeijon.crmpipedriveintegration.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 06/03/2021 às 22:05:17
 * 
 * @author gregory.feijon
 */

@Getter
@Setter
public class JwtProperties {

	private String signKey;
	private String keyType;
}
