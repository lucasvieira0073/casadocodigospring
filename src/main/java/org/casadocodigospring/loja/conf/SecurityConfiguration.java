package org.casadocodigospring.loja.conf;

import org.casadocodigospring.loja.daos.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // diz para o spring que é uma classe de configuração de segurança
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
	
	@Override//ideal é fazer todos os bloqueios primeiro e depois as liberações
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/produtos/form").hasRole("ADMIN")
			.antMatchers("/carrinho/**").permitAll()
			.antMatchers("/pagamento/**").permitAll()
			.antMatchers(HttpMethod.POST,"/produtos").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/produtos/**").permitAll()//permitindo tudo oque NÃO foi liberado anteriormente
			.antMatchers("/").permitAll()
			.antMatchers("/url-magica-maluca-sa545688sjkdsjlfhn554dk554lfnodfklgnfngfjdgn54546").permitAll()
			.antMatchers("/url-magica-doida-sa545688sjkdsjlfhn554dk554lfnodfklgnfngfjdgn54546").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));	//essa ultima linha serve para conseguir fazer o logout pelo metodo GET tambem			
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDao)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
}
