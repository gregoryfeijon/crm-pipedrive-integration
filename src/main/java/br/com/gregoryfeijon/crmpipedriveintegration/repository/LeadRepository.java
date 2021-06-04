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

import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.util.GsonUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ValidationHelpers;

/**
 * 30/05/2021 às 17:07:28
 * 
 * @author gregory.feijon
 */

@Repository
public class LeadRepository extends FileRepository<Lead> {

	private static final String USUARIO_JSON_PATH = "./src/main/resources/leads.json";
	private static final Gson GSON_UTIL = GsonUtil.getGson();

	public Optional<Lead> salvaLead(Lead leadSalvar) {
		try {
			File leadsFile = new File(USUARIO_JSON_PATH);
			if (leadsFile.exists() && leadsFile.canWrite()) {
				String leadsJson = readFromFile(leadsFile);
				List<Lead> leads = GSON_UTIL.fromJson(leadsJson, returnType().getType());
				verificaLeadsAtualizaExistente(leads, leadSalvar);
				String jsonSalvar = GSON_UTIL.toJson(leads);
				Files.write(leadsFile.toPath(), jsonSalvar.getBytes("utf-8"), StandardOpenOption.WRITE);
			}
		} catch (IOException ex) {
			throw new APIException("Erro ao salvar usuário.");
		}
		return Optional.of(leadSalvar);
	}

	private void verificaLeadsAtualizaExistente(List<Lead> leads, Lead leadSalvar) {
		if (ValidationHelpers.collectionNotEmpty(leads)) {
			if (leadSalvar.getId() == 0) {
				leadSalvar.setId(leads.stream().mapToLong(Lead::getId).max().getAsLong());
			}
			leads.stream().filter(leadSalvo -> leadSalvo.getId() == leadSalvar.getId()).findAny()
					.ifPresentOrElse(leadExistente -> leadExistente = leadSalvar, () -> leads.add(leadSalvar));
		} else {
			if (leadSalvar.getId() == 0) {
				leadSalvar.setId(1);
			}
			leads.add(leadSalvar);
		}
	}

	public List<Lead> obtemLeads() {
		List<Lead> leads = new ArrayList<>();
		try {
			File leadsFile = new File(USUARIO_JSON_PATH);
			if (leadsFile.exists() && leadsFile.canWrite()) {
				String leadsJson = readFromFile(leadsFile);
				leads = GSON_UTIL.fromJson(leadsJson, returnType().getType());
			}
		} catch (IOException ex) {
			throw new APIException("Erro ao obter usuários.");
		}
		return leads;
	}

	public void limpaLeads() {
		try {
			File leadsFile = new File(USUARIO_JSON_PATH);
			if (leadsFile.exists() && leadsFile.canWrite()) {
				String jsonLimpa = "";
				Files.write(leadsFile.toPath(), jsonLimpa.getBytes("utf-8"), StandardOpenOption.CREATE_NEW);
			}
		} catch (IOException ex) {
			throw new APIException("Erro ao limpar usuários.");
		}
	}

	@Override
	protected ParameterizedTypeReference<List<Lead>> returnType() {
		return new ParameterizedTypeReference<List<Lead>>() {
		};
	}
}
