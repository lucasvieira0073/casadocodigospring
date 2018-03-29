package org.casadocodigospring.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import org.casadocodigospring.loja.builders.ProdutoBuilder;
import org.casadocodigospring.loja.conf.DataSourceConfigurationTest;
import org.casadocodigospring.loja.conf.JPAConfiguration;
import org.casadocodigospring.loja.models.Produto;
import org.casadocodigospring.loja.models.TipoPreco;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class})
@ActiveProfiles("teste")
public class ProdutoDAOTeste {
	
	
	@Autowired
	private ProdutoDAO produtoDao;
	
	@Test
	@Transactional
	public void deveSomarTodosPrecosPorTipoPreco() {
		
		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).more(3).buildAll();
		
		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN).more(3).buildAll();
		
		livrosImpressos.stream().forEach(produtoDao::gravar);//grava cada produto com lambda
		livrosEbook.stream().forEach(produtoDao::gravar);
		
		BigDecimal valor = produtoDao.somaPrecosPorTipo(TipoPreco.EBOOK);
		
		//o primeiro é o valor esperador e o segundo é o valor para comparação
		//setScale é o numero de casas decimais
		Assert.assertEquals(new BigDecimal(40).setScale(2), valor);
		
	}
	
}
