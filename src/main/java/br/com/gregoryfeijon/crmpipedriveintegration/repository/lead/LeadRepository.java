package br.com.gregoryfeijon.crmpipedriveintegration.repository.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.dto.LeadFinalizaDTO;
import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.FileRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.util.GsonUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.StringUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ValidationHelpers;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 30/05/2021 às 17:07:28
 * 
 * @author gregory.feijon
 */

@Repository
public class LeadRepository extends FileRepository<Lead> implements ILeadRepository {

	private static final String USUARIO_JSON_PATH = "./src/main/resources/dados/leads.json";
	private static final Gson GSON_UTIL = GsonUtil.getGson();

	@Override
	public Optional<Lead> save(Lead leadSalvar) {
		try {
			File leadsFile = new File(USUARIO_JSON_PATH);
			if (leadsFile.exists() && leadsFile.canWrite()) {
				String leadsJson = readFromFile(leadsFile);
				List<Lead> leads = GSON_UTIL.fromJson(leadsJson, returnType().getType());
				leads = verificaLeadsAtualizaExistente(leads, leadSalvar);
				String jsonSalvar = GSON_UTIL.toJson(leads);
				PrintWriter writer = new PrintWriter(leadsFile);
				writer.write(jsonSalvar);
				writer.close();
			}
		} catch (IOException ex) {
			throw new APIException("Erro ao salvar usuário.");
		}
		return Optional.of(leadSalvar);
	}

	private List<Lead> verificaLeadsAtualizaExistente(List<Lead> leads, Lead leadSalvar) {
		if (ValidationHelpers.collectionNotEmpty(leads)) {
			if (leadSalvar.getId() == 0) {
				long maxId = leads.stream().mapToLong(Lead::getId).max().getAsLong();
				leadSalvar.setId(maxId + 1);
			}
			Optional<Lead> opLead = leads.stream().filter(leadSalvo -> leadSalvo.getId() == leadSalvar.getId())
					.findAny();
			if (opLead.isPresent()) {
				leads = leads.stream().map(lead -> lead.getId() == leadSalvar.getId() ? leadSalvar : lead).collect(Collectors.toList());
			} else {
				leads.add(leadSalvar);
			}
		} else {
			if (leadSalvar.getId() == 0) {
				leadSalvar.setId(1);
			}
			return criaLista(leads, leadSalvar);
		}
		return leads;
	}

	private List<Lead> criaLista(List<Lead> leads, Lead leadSalvar) {
		leads = new ArrayList<>();
		leads.add(leadSalvar);
		return leads;
	}
	
	public Optional<Lead> saveLeadStatus(LeadFinalizaDTO leadFinalizaDTO) {
		return saveLeadStatus(new Lead(leadFinalizaDTO));
	}

	@Override
	public Optional<Lead> saveLeadStatus(Lead lead) {
		List<Lead> leads = listAll();
		Optional<Lead> opLeadAlterar = leads.stream().filter(leadSalvo -> leadSalvo.getId() == leadSalvo.getId())
				.findFirst();
		if (!opLeadAlterar.isPresent()) {
			throw new APIException("Não foi possível encontrar o lead especificado!");
		}
		opLeadAlterar.get().setStatus(lead.getStatus());
		return save(opLeadAlterar.get());
	}

	@Override
	public List<Lead> listAll() {
		List<Lead> leads = new ArrayList<>();
		try {
			File leadsFile = new File(USUARIO_JSON_PATH);
			if (leadsFile.exists() && leadsFile.canWrite()) {
				String leadsJson = readFromFile(leadsFile);
				if (StringUtil.isNotNull(leadsJson)) {
					leads = GSON_UTIL.fromJson(leadsJson, returnType().getType());
				}
			}
		} catch (IOException ex) {
			throw new APIException("Erro ao obter usuários.");
		}
		return leads;
	}

	@Override
	public void deleteAll() {
		try {
			File leadsFile = new File(USUARIO_JSON_PATH);
			if (leadsFile.exists() && leadsFile.canWrite()) {
				new PrintWriter(USUARIO_JSON_PATH).close();
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
