package org.casadocodigospring.loja.controllers;

import java.util.concurrent.Callable;

import org.casadocodigospring.loja.models.CarrinhoCompras;
import org.casadocodigospring.loja.models.DadosPagamento;
import org.casadocodigospring.loja.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/pagamento")//request mapping não é necessário , serve para encurar nossa chamada de link e não precisar ficar escrevendo /pagamento
@Controller
public class PagamentoController {
	
	@Autowired
	private MailSender sender;
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)//@AuthenticationPrincipal faz o springsecurity nos trazer o usuario
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario,RedirectAttributes model) {
		return () -> {
			String uri = "http://book-payment.herokuapp.com/payment";
			
			try {
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				System.out.println(response);
			
				enviaEmailCompraProduto(usuario);
				
				model.addFlashAttribute("sucesso", response);
				return new ModelAndView("redirect:/produtos");
			}catch(HttpClientErrorException e) {
				model.addFlashAttribute("falha", "Valor maior que o permitido");
				return new ModelAndView("redirect:/produtos");
			}
		};
		
	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra finalizada com sucesso");//assunto
		//email.setTo(usuario.getEmail());//destino
		email.setTo("lucas.vieira.dasilva@bol.com.br");//destino
		email.setText("Compra foi aprovada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("lucas.vieira.dasilva@bol.com.br");//origem
		
		sender.send(email);
	}
}
