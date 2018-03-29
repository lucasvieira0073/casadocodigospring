package org.casadocodigospring.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.casadocodigospring.loja.models.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAO implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Usuario loadUserByUsername(String email) {
		List<Usuario> usuarios = manager.createQuery("select u from Usuario u "
				+ "where u.email = :email", Usuario.class).setParameter("email", email)
					.getResultList();
		
		if(usuarios.isEmpty()) {
			throw new UsernameNotFoundException("Usuário " + email + " não foi encontrado!");
		}
		
		return usuarios.get(0);
	}

	public void gravar(Usuario usuario) {
		manager.persist(usuario);
	}
	
	public void remover(Usuario usuario) {
		manager.remove(usuario);
	}

	
	public Usuario find(String email) {
		Usuario usuario = manager.createQuery("select u from Usuario u "
				+ "where u.email = :email", Usuario.class).setParameter("email", email)
					.getSingleResult();
		
		return usuario;
	}
	
}
