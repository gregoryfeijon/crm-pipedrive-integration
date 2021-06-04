package br.com.gregoryfeijon.crmpipedriveintegration.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.validation.constraints.NotNull;

import br.com.gregoryfeijon.crmpipedriveintegration.exception.APIException;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;

/**
 * 02/06/2021 às 23:36:22
 * 
 * @author gregory.feijon
 */

public final class FilaLeads {

	private static final LoggerUtil LOG = LoggerUtil.getLog(FilaLeads.class);

	private FilaLeads() {}

	private static BlockingQueue<Lead> LEADS_QUEUE;

	public static synchronized void addLead(@NotNull Lead lead) {
		verificaInicializaQueue();
		try {
			LEADS_QUEUE.put(lead);
			LOG.info("Lead adicionado com sucesso!");
		} catch (InterruptedException e) {
			throw new APIException("Adição de LEAD à fila foi interrompida inesperadamente!");
		}
	}

	public static synchronized Lead getLead() {
		try {
			return LEADS_QUEUE.take();
		} catch (InterruptedException e) {
			throw new APIException("Adição de LEAD à fila foi interrompida inesperadamente!");
		}
	}

	private static void verificaInicializaQueue() {
		if (LEADS_QUEUE == null) {
			LEADS_QUEUE = new LinkedBlockingQueue<>();
		}
	}
}
