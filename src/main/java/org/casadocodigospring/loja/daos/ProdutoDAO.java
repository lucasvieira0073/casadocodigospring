package org.casadocodigospring.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.casadocodigospring.loja.models.Produto;
import org.casadocodigospring.loja.models.TipoPreco;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional//informa que esse dao terá transação é o transactional do pacote do spring
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager manager;	
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	//com join fetch ele traz tambem as listas relacionadas
	//com o distinct ele tras apenas um Produto com os preços , sem o distinct ele traz 1 produto para cada preco, neste caso 3 produtos serao criados de cada
	//ele so retorna se outro produto se for diferente
	public List<Produto> listar() {
		//return manager.createQuery("select distinct(p) from Produto p join fetch p.precos", Produto.class).getResultList();
		return manager.createQuery("select p from Produto p", Produto.class).getResultList();
	}

	public Produto find(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos preco where p.id = :id", Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	public BigDecimal somaPrecosPorTipo(TipoPreco tipoPreco) {
		TypedQuery<BigDecimal> query = manager.createQuery("select sum(preco.valor) from "
				+ "Produto p join p.precos preco where preco.tipo = :tipoPreco", BigDecimal.class);
		
		query.setParameter("tipoPreco", tipoPreco);
		
		return query.getSingleResult();
	}
	
}
