package br.com.gregoryfeijon.crmpipedriveintegration.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.util.GsonUtil;

/**
 * 30/05/2021 às 17:07:28
 * 
 * @author gregory.feijon
 */

@Repository
public class LeadRepository extends FileRepository<Lead> {

	private static final String USUARIO_JSON_PATH = "src/main/resources/leads.json";
	private static final Gson GSON_UTIL = GsonUtil.getGson();

	public Optional<Lead> salvaLead(Lead leadSalvar) throws IOException {
		File leadsFile = new File(USUARIO_JSON_PATH);
		if (leadsFile.exists() && leadsFile.canWrite()) {
			String leadsJson = readFromFile(leadsFile);
			List<Lead> leads = GSON_UTIL.fromJson(leadsJson, returnType().getType());
			leads.add(leadSalvar);
			String jsonSalvar = GSON_UTIL.toJson(leads);
			Files.write(leadsFile.toPath(), jsonSalvar.getBytes("utf-8"), StandardOpenOption.WRITE);
		}
		return Optional.of(leadSalvar);
	}

	public List<Lead> obtemLeads() throws IOException {
		List<Lead> leads = new ArrayList<>();
		File leadsFile = new File(USUARIO_JSON_PATH);
		if (leadsFile.exists() && leadsFile.canWrite()) {
			String leadsJson = readFromFile(leadsFile);
			leads = GSON_UTIL.fromJson(leadsJson, returnType().getType());
		}
		return leads;
	}

	@Override
	protected ParameterizedTypeReference<List<Lead>> returnType() {
		return new ParameterizedTypeReference<List<Lead>>() {
		};
	}
}
