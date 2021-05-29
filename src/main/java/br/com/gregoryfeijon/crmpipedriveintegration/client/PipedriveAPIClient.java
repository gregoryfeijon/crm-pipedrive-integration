package br.com.gregoryfeijon.crmpipedriveintegration.client;

import java.util.Optional;

/**
29/05/2021 às 16:29:35

@author gregory.feijon
*/

public abstract class PipedriveAPIClient<T> extends APIClient<T> {

	public abstract Optional<T> sendDeal(T entity);
}
