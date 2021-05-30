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

import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.util.GsonUtil;

/**
 * 30/05/2021 às 17:07:20
 * 
 * @author gregory.feijon
 */

@Repository
public class UsuarioRepository extends FileRepository<Usuario> {

	private static final String USUARIO_JSON_PATH = "src/main/resources/usuarios.json";
	private static final Gson GSON_UTIL = GsonUtil.getGson();

	public Optional<Usuario> salvaUsuario(Usuario usuarioSalvar) throws IOException {
		File usuariosFile = new File(USUARIO_JSON_PATH);
		if (usuariosFile.exists() && usuariosFile.canWrite()) {
			String usuariosJson = readFromFile(usuariosFile);
			List<Usuario> usuarios = GSON_UTIL.fromJson(usuariosJson, returnType().getType());
			usuarios.add(usuarioSalvar);
			String jsonSalvar = GSON_UTIL.toJson(usuarios);
			Files.write(usuariosFile.toPath(), jsonSalvar.getBytes("utf-8"), StandardOpenOption.WRITE);
		}
		return Optional.of(usuarioSalvar);
	}

	public List<Usuario> obtemUsuarios() throws IOException {
		List<Usuario> usuarios = new ArrayList<>();
		File usuariosFile = new File(USUARIO_JSON_PATH);
		if (usuariosFile.exists() && usuariosFile.canWrite()) {
			String usuariosJson = readFromFile(usuariosFile);
			usuarios = GSON_UTIL.fromJson(usuariosJson, returnType().getType());
		}
		return usuarios;
	}

	@Override
	protected ParameterizedTypeReference<List<Usuario>> returnType() {
		return new ParameterizedTypeReference<List<Usuario>>() {
		};
	}
}
