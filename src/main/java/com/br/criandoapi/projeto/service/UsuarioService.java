package com.br.criandoapi.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.criandoapi.projeto.model.Usuario;
import com.br.criandoapi.projeto.repository.IUsuario;

@Service
public class UsuarioService {
	
	private IUsuario repository;
	private PasswordEncoder passwordEncoder;
	
	public UsuarioService(IUsuario repository) {
		this.repository = repository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public List<Usuario> listarUsuario() {
		List<Usuario> lista = repository.findAll();
		return lista;	
	}
	
	public Usuario criarUsuario(Usuario usuario) {
		String encoder = this.passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(encoder);
		Usuario usuarioNovo = repository.save(usuario);
		return usuarioNovo;
	}
	
	public Boolean editarUsuario(Usuario usuarioAtualizado, Integer id) {
		if (repository.existsById(id)) {
			String encoder = this.passwordEncoder.encode(usuarioAtualizado.getSenha());
			usuarioAtualizado.setSenha(encoder);
			Usuario usuarioExistente = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuáio não encontrado"));
			BeanUtils.copyProperties(usuarioAtualizado, usuarioExistente, "id");
			repository.save(usuarioExistente);
			return true;
		} 
		return false;
	}
	
	public Boolean excluirUsuario(Integer id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		} 
		return false;
	}

	public Boolean validarSenha(Usuario usuario) {
		Optional<Usuario> optionalUsuario = repository.findById(usuario.getId());
		
		if (optionalUsuario.isPresent()) {
			String senha = optionalUsuario.get().getSenha();
			return passwordEncoder.matches(usuario.getSenha(), senha);
		} 
		return false;
	}
}