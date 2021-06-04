package br.com.gregoryfeijon.crmpipedriveintegration.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;

/**
 * 01/06/2021 às 23:32:05
 * 
 * @author gregory.feijon
 */

public final class CollectionUtil {

	private CollectionUtil() {}

	public static <T> void addIfAbsent(Collection<T> entities1, Collection<T> entities2) {
		if (entities1 == null) {
			throw new APIException(
					"A lista para a qual está tentando copiar os valores está nula! Favor, inicialize-a antes de utilizar esse método.");
		}
		if (ValidationHelpers.collectionEmpty(entities1)) {
			entities1.addAll(entities2);
		} else {
			entities2.stream().forEach(entity -> {
				if (!entities1.contains(entity)) {
					entities1.add(entity);
				}
			});
		}
	}
	
	public static <T> void addIfAbsent(Collection<T> entities, T entity) {
		if (entities == null) {
			throw new APIException(
					"A lista para a qual está tentando copiar os valores está nula! Favor, inicialize-a antes de utilizar esse método.");
		}
		if (ValidationHelpers.collectionEmpty(entities)) {
			entities.add(entity);
		} else {
			if (!entities.contains(entity)) {
				entities.add(entity);
			}
		}
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
