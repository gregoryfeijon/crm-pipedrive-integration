package br.com.gregoryfeijon.crmpipedriveintegration.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 
 * 30/05/2021 às 17:30:28
 * 
 * @author gregory.feijon
 * 
 */

public final class ValidationHelpers {

	private ValidationHelpers() {}

	private static Predicate<Collection<?>> predicateCollection;
	private static Predicate<Map<?, ?>> predicateMap;
	private static Predicate<Object> predicateIsNull;

	public static boolean collectionEmpty(Collection<?> entities) {
		if (predicateCollection == null) {
			criaPredicateCollectionVazia();
		}
		return predicateCollection.test(entities);
	}

	public static boolean collectionNotEmpty(Collection<?> entities) {
		return !collectionEmpty(entities);
	}

	private static void criaPredicateCollectionVazia() {
		predicateCollection = collection -> collection == null || collection.isEmpty();
	}

	public static boolean mapEmpty(Map<?, ?> map) {
		if (predicateMap == null) {
			criaPredicateMapVazia();
		}
		return predicateMap.test(map);
	}

	public static boolean mapNotEmpty(Map<?, ?> map) {
		return !mapEmpty(map);
	}

	private static void criaPredicateMapVazia() {
		predicateMap = map -> map == null || map.isEmpty();
	}

	public static boolean isNull(Object entity) {
		if (predicateIsNull == null) {
			criaPredicateIsNull();
		}
		return predicateIsNull.test(entity);
	}

	public static boolean isNotNull(Object entity) {
		return !isNull(entity);
	}

	private static void criaPredicateIsNull() {
		predicateIsNull = Objects::isNull;
	}

	public static List<String> processaErros(Map<String, Boolean> map) {
		Predicate<Boolean> p = criaPredicateProcessaErros();
		List<String> erros = new LinkedList<>();
		map.forEach((mensagem, v) -> {
			if (p.test(v)) {
				erros.add(mensagem);
			}
		});
		return erros;
	}

	private static Predicate<Boolean> criaPredicateProcessaErros() {
		return p -> !p;
	}

	public static List<String> montaListaErro(String mensagemInicial, List<String> erros) {
		List<String> errosAdd = new LinkedList<>(Collections.singletonList(mensagemInicial));
		errosAdd.addAll(erros);
		erros.addAll(errosAdd);
		return errosAdd;
	}
}
