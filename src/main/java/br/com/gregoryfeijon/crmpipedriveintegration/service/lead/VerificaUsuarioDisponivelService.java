package br.com.gregoryfeijon.crmpipedriveintegration.service.lead;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Lead;
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.service.usuario.ListAllUsuariosService;
import br.com.gregoryfeijon.crmpipedriveintegration.util.LoggerUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ValidationHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 25/01/2022 às 22:23
 *
 * @author gregory.feijon
 */

@Service
public class VerificaUsuarioDisponivelService {

    private static final LoggerUtil LOG = LoggerUtil.getLog(VerificaUsuarioDisponivelService.class);

    @Autowired
    private ListAllLeadsService listAllLeadsService;

    @Autowired
    private ListAllUsuariosService listAllUsuariosService;

    @Autowired
    private AtualizaFilaLeadsService atualizaFilaLeadsService;

    @Autowired
    private SaveLeadService saveLeadService;

    public void execute(Lead lead) {
        List<Usuario> usuarios = listAllUsuariosService.listAll().stream().sorted(Comparator.comparing(Usuario::getId))
                .collect(Collectors.toList());
        Map<Long, List<Lead>> mapaUsuarioLeads = listAllLeadsService.listAll().stream().filter(l -> l.getUsuarioResponsavelId() != null)
                .collect(Collectors.groupingBy(Lead::getUsuarioResponsavelId));
        if (ValidationHelpers.mapNotEmpty(mapaUsuarioLeads)) {
            atribuiLeadUsuario(usuarios, mapaUsuarioLeads.keySet(), lead);
            atualizaFilaLeadsService.execute(mapaUsuarioLeads, lead);
        } else {
            Usuario firstUser = usuarios.stream().findFirst().get();
            lead.setUsuarioResponsavelId(firstUser.getId());
        }
        saveLeadService.execute(lead);
    }

    private void atribuiLeadUsuario(List<Usuario> usuarios, Set<Long> idUsuariosComLead, Lead lead) {
        usuarios.stream().forEach(usuario -> {
            if (lead.getUsuarioResponsavelId() == null) {
                boolean usuarioSemLead = idUsuariosComLead.stream()
                        .noneMatch(usuarioId -> usuarioId == usuario.getId());
                if (usuarioSemLead) {
                    lead.setUsuarioResponsavelId(usuario.getId());
                }
            }
        });
    }
}
