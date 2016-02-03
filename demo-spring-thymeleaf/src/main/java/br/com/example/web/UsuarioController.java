package br.com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.example.service.UsuarioService;

@Controller
public class UsuarioController {

	@Autowired private UsuarioService usuarioService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("listaDeUsuario", usuarioService.listaDeUsuarios());
		return "usuarios";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String login() {
		return "login";
	}

}
