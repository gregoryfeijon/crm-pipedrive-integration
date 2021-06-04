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
import br.com.gregoryfeijon.crmpipedriveintegration.model.Usuario;
import br.com.gregoryfeijon.crmpipedriveintegration.util.GsonUtil;
import br.com.gregoryfeijon.crmpipedriveintegration.util.ValidationHelpers;

/**
 * 30/05/2021 às 17:07:20
 * 
 * @author gregory.feijon
 */

@Repository
public class UsuarioRepository extends FileRepository<Usuario> {

	private static final String USUARIO_JSON_PATH = "./src/main/resources/usuarios.json";
	private static final Gson GSON_UTIL = GsonUtil.getGson();

	public Optional<Usuario> salvaUsuario(Usuario usuarioSalvar) {
		try {
			File usuariosFile = new File(USUARIO_JSON_PATH);
			if (usuariosFile.exists() && usuariosFile.canWrite()) {
				String usuariosJson = readFromFile(usuariosFile);
				List<Usuario> usuarios = GSON_UTIL.fromJson(usuariosJson, returnType().getType());
				verificaUsuariosAtualizaExistente(usuarios, usuarioSalvar);
				String jsonSalvar = GSON_UTIL.toJson(usuarios);
				Files.write(usuariosFile.toPath(), jsonSalvar.getBytes("utf-8"), StandardOpenOption.WRITE);
			}
		} catch (IOException ex) {
			throw new APIException("Erro ao salvar usuário.");
		}
		return Optional.of(usuarioSalvar);
	}

	private void verificaUsuariosAtualizaExistente(List<Usuario> usuarios, Usuario usuarioSalvar) {
		if (ValidationHelpers.collectionNotEmpty(usuarios)) {
			if (usuarioSalvar.getId() == 0) {
				usuarioSalvar.setId(usuarios.stream().mapToLong(Usuario::getId).max().getAsLong());
			}
			usuarios.stream().filter(usuarioSalvo -> usuarioSalvo.getId() == usuarioSalvar.getId()).findAny()
					.ifPresentOrElse(usuarioExistente -> usuarioExistente = usuarioSalvar,
							() -> usuarios.add(usuarioSalvar));
		} else {
			if (usuarioSalvar.getId() == 0) {
				usuarioSalvar.setId(1);
			}
			usuarios.add(usuarioSalvar);
		}
	}

	public List<Usuario> obtemUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		try {
			File usuariosFile = new File(USUARIO_JSON_PATH);
			if (usuariosFile.exists() && usuariosFile.canWrite()) {
				String usuariosJson = readFromFile(usuariosFile);
				usuarios = GSON_UTIL.fromJson(usuariosJson, returnType().getType());
			}
		} catch (IOException ex) {
			throw new APIException("Erro ao obter usuários.");
		}
		return usuarios;
	}

	public void limpaUsuarios() {
		try {
			File usuariosFile = new File(USUARIO_JSON_PATH);
			if (usuariosFile.exists() && usuariosFile.canWrite()) {
				String jsonLimpa = "";
				Files.write(usuariosFile.toPath(), jsonLimpa.getBytes("utf-8"), StandardOpenOption.CREATE_NEW);
			}
		} catch (IOException ex) {
			throw new APIException("Erro ao limpar usuários.");
		}
	}

	@Override
	protected ParameterizedTypeReference<List<Usuario>> returnType() {
		return new ParameterizedTypeReference<List<Usuario>>() {
		};
	}
}
