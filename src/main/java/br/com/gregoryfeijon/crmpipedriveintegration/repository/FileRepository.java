package br.com.gregoryfeijon.crmpipedriveintegration.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

/**
30/05/2021 às 17:21:43

@author gregory.feijon
*/

public abstract class FileRepository<T> {

	protected String readFromFile(File file) throws IOException {
		byte[] bytes = Files.readAllBytes(file.toPath());
		String json = new String(bytes, "utf8");
		return json;
	}
	
	protected abstract ParameterizedTypeReference<List<T>> returnType();
}
