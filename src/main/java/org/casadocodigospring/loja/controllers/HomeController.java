package org.casadocodigospring.loja.controllers;

import java.util.Arrays;
import java.util.List;

import org.casadocodigospring.loja.daos.ProdutoDAO;
import org.casadocodigospring.loja.daos.UsuarioDAO;
import org.casadocodigospring.loja.models.Produto;
import org.casadocodigospring.loja.models.Role;
import org.casadocodigospring.loja.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
	@RequestMapping("/")
	@Cacheable(value="produtosHome")
	public ModelAndView index() {
		List<Produto> produtos = produtoDao.listar();
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-maluca-sa545688sjkdsjlfhn554dk554lfnodfklgnfngfjdgn54546")
	public String urlMagicaMaluca() {
		Usuario usuario = new Usuario();
		usuario.setNome("Admin");
		usuario.setEmail("admin@casadocodigo.com.br");
		usuario.setSenha("$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu");
		usuario.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
		
		usuarioDao.gravar(usuario);
		
		return "Url mágica executada";
	}
	
	
	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-doida-sa545688sjkdsjlfhn554dk554lfnodfklgnfngfjdgn54546")
	public String urlMagicaDoida() {
		
		Usuario usuario = usuarioDao.find("admin@casadocodigo.com.br");
		
		usuarioDao.remover(usuario);
		
		return "Url mágica executada";
	}
	
	
}




