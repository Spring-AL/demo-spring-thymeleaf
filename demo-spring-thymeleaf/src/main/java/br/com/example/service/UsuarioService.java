package br.com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.example.domain.Usuario;
import br.com.example.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> listaDeUsuarios() {
		return usuarioRepository.findAll();
	}

}
