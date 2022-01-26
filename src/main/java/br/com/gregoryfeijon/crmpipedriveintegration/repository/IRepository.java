package br.com.gregoryfeijon.crmpipedriveintegration.repository;

import java.util.List;
import java.util.Optional;

/**
 * 25/01/2022 às 15:02
 *
 * @author gregory.feijon
 */

public interface IRepository<T> {

    Optional<T> save(T entity);

    List<T> listAll();

    void deleteAll();
}
