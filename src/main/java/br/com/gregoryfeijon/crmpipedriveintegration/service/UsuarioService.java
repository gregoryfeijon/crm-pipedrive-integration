package br.com.gregoryfeijon.crmpipedriveintegration.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.repository.UsuarioRepository;

/**
 * 03/06/2021 às 19:21:40
 * 
 * @author gregory.feijon
 */

@Service
public class UsuarioService implements IService<Usuario> {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Optional<Usuario> save(Usuario usuario) {
		return usuarioRepository.salvaUsuario(usuario);
	}

	@Override
	public List<Usuario> listAll() {
		return usuarioRepository.obtemUsuarios();
	}
}
