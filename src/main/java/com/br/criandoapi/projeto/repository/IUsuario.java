package com.br.criandoapi.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.criandoapi.projeto.model.Usuario;

public interface IUsuario extends JpaRepository<Usuario, Integer>{

}
