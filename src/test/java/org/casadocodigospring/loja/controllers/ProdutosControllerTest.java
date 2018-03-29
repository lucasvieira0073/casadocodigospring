package org.casadocodigospring.loja.controllers;

import javax.servlet.Filter;

import org.casadocodigospring.loja.conf.AppWebConfiguration;
import org.casadocodigospring.loja.conf.DataSourceConfigurationTest;
import org.casadocodigospring.loja.conf.JPAConfiguration;
import org.casadocodigospring.loja.conf.SecurityConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {JPAConfiguration.class, AppWebConfiguration.class, DataSourceConfigurationTest.class, SecurityConfiguration.class})
@ActiveProfiles("teste")
public class ProdutosControllerTest {
	
	@Autowired //contexto
	private WebApplicationContext wac;

	//ele finge ser o MVC
	private MockMvc mockMvc;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	@Before//roda algo antes do teste
	public void setup() {
		//this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();//cria o mockMvc
		
		//assim é craido o mock quando precisamos utilizar o spring security test
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilter(springSecurityFilterChain).build();
	}
	
	@Test
	public void deveRetornarParaHomeComOsLivros() throws Exception {
		//faz uma requisição
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))//virifica se existe o atributo produtos
			.andExpect(MockMvcResultMatchers   //andExpect é oque o mock espera com essa requisição
					.forwardedUrl("/WEB-INF/views/home.jsp"));//verifica se na requisição o mock foi enviado para home.jsp
	}
	
	@Test
	public void somenteAdminDeveAcessarProdutosForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form")
			.with(SecurityMockMvcRequestPostProcessors    // serve para processar os requests (faz parte do spring security)
					.user("lucasvieira0073@hotmail.com").password("123456")
					.roles("USUARIO")))
			.andExpect(MockMvcResultMatchers.status().is(403));//como tem q ter a role ADMIN se espera o resultado 403 (acesso negado)
	}
}
