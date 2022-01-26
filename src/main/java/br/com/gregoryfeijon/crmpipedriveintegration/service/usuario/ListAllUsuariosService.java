package br.com.gregoryfeijon.crmpipedriveintegration.service.usuario;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.usuario.IUsuarioRepository;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllUsuariosService {

    private final IUsuarioRepository usuarioRepository;

    public ListAllUsuariosService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listAll() {
        return usuarioRepository.listAll();
    }
}
