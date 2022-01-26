package br.com.gregoryfeijon.crmpipedriveintegration.service;

import java.util.List;
import java.util.Optional;

/**
 * 31/05/2021 às 20:36:41
 * 
 * @author gregory.feijon
 */

public interface IService<T> {

	Optional<T> save(T entity);

	List<T> listAll();
}
