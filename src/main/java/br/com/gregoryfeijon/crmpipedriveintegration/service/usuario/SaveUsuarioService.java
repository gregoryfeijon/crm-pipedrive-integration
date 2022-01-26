package br.com.gregoryfeijon.crmpipedriveintegration.service.usuario;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.usuario.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaveUsuarioService {

    private final IUsuarioRepository usuarioRepository;

    public SaveUsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
