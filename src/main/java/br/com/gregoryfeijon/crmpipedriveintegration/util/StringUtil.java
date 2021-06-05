package br.com.gregoryfeijon.crmpipedriveintegration.util;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 26/05/2021 às 23:35:04
 *
 * @author gregory.feijon
 */

public final class StringUtil {

	private StringUtil() {}

	public static String somenteNumeros(String string) {
		return somenteNumeros(string, null);
	}

	public static String somenteNumeros(String string, char... ignorados) {
		if (isNull(string)) {
			return string;
		}
		Set<Character> ignoradosSet = obtemIgnorados(ignorados);
		StringBuilder sb = new StringBuilder();
		obtemStreamChars(string.toCharArray()).forEach(character -> {
			if (ignoradosSet.contains(character)) {
				sb.append(character);
			} else if (Character.isDigit(character)) {
				sb.append(character);
			}
		});
		return sb.toString();
	}

	private static Set<Character> obtemIgnorados(char[] ignorados) {
		if (ignorados == null) {
			return Collections.emptySet();
		}
		return obtemStreamChars(ignorados).collect(Collectors.toSet());
	}

	public static Stream<Character> obtemStreamChars(char[] chars) {
		return IntStream.range(0, chars.length).mapToObj(i -> chars[i]);
	}

	public static boolean isNull(String string) {
		return string == null || string.trim().equals("");
	}
	
	public static boolean isNotNull(String string) {
		return !isNull(string);
	}
}
