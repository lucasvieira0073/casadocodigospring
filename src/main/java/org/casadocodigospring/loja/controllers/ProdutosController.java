package org.casadocodigospring.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.casadocodigospring.loja.daos.ProdutoDAO;
import org.casadocodigospring.loja.infra.FileSaver;
import org.casadocodigospring.loja.models.Produto;
import org.casadocodigospring.loja.models.TipoPreco;
import org.casadocodigospring.loja.validation.ProdutoValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private FileSaver fileSaver;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}
	
	@RequestMapping("form")
	public ModelAndView form(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");//constroi informando qual view ira acessar
		modelAndView.addObject("tipos", TipoPreco.values());

		return modelAndView;
	}
	
	
	@RequestMapping(method=RequestMethod.POST)//estaria com o parametro dentro value="produtos", method=RequestMethod.POST, mas como é o mesmo path da raiz do controller nao precisa
	@CacheEvict(value="produtosHome", allEntries=true)
	public ModelAndView gravar(MultipartFile sumario,@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			return form(produto);
		}
		
		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		
		produtoDao.gravar(produto);
		
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");//cria um escopo flash
		
		
		
		return new ModelAndView("redirect:produtos");//será redirecionado via get (esta é a melhor pratica)
	}
	
	
	@RequestMapping(method=RequestMethod.GET)//estaria com o parametro dentro value="produtos", method=RequestMethod.GET, mas como é o mesmo path da raiz do controller nao precisa
	public ModelAndView listar() {
		List<Produto> produtos = produtoDao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}") //cria uma url amigavel exemplo : casadocodigospring/produtos/detalhe/5, o tradicional é só /detalhe ai pode enviar com get normal
	public ModelAndView detalhe(@PathVariable("id") Integer id) {//@PathVariable é para a variavel de url amigavel, se fosse com get normal não precisa
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		
		Produto produto = produtoDao.find(id);
		
		modelAndView.addObject("produto", produto);
		
		return modelAndView;
	}
	
	//porem serve apenas para retornar json, o correto é configurar um content negotiation
	//exemplo de recurso em json
	//@RequestMapping("/{id}") 
	//@ResponseBody // tudo oque ele retornar vai ser o corpo da resposta e não um jsp por exemplo
	//public Produto detalheJson(@PathVariable("id") Integer id) {
		//return produtoDao.find(id);
	//}
	
	
	//pode tratar qualquer exceção se colocar Exception.class
	//@ExceptionHandler(Exception.class)
	//@ExceptionHandler(NoResultException.class)
	//public String trataDetalheNaoEncontrado() {
		//return "error";
	//}
	
}




