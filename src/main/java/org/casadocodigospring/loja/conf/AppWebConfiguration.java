package org.casadocodigospring.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.casadocodigospring.loja.controllers.HomeController;
import org.casadocodigospring.loja.daos.ProdutoDAO;
import org.casadocodigospring.loja.infra.FileSaver;
import org.casadocodigospring.loja.models.CarrinhoCompras;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

@EnableWebMvc
//@ComponentScan(basePackages={"org.casadocodigospring.loja.controllers"})
@ComponentScan(basePackageClasses= {HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class})
@EnableCaching //para utilzar cache e tambem precisa implementar o metodo cacheManager()
public class AppWebConfiguration extends WebMvcConfigurerAdapter{ //eu extendi o webmvcadapter para conseguir o metodo para acesasr os recursos css ...
	
	@Bean // bean é uma classe gerenciada pelo spring
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		//resolver.setExposeContextBeansAsAttributes(true);//todos os beans ficam os metodos disponiveis como atributos na jsp perigosoooo
		resolver.setExposedContextBeanNames("carrinhoCompras");//expoe os metodos do bean especifico
		
		return resolver;
	}
	
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		
		return messageSource;
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	@Bean //para receber files 
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	
	//metodo para criar um rest template
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean //método para controle do cache utilizando guava
	public CacheManager cacheManager() {
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
		.maximumSize(100) //tamanho do cache 100 elementos
		.expireAfterAccess(5, TimeUnit.MINUTES); // tempo para expirar o cache pode ser 1 DIA depenendendo do uso do cache , esta em minutos para testarmos a aplicação
		
		
		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(builder);
		
		
		return manager;
	}
	
	@Bean
	public  ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager) {
		List<ViewResolver> viewResolvers = new ArrayList<>();
		viewResolvers.add(internalResourceViewResolver());
		viewResolvers.add(new JsonViewResolver());
		
		 ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver(); 
		 resolver.setViewResolvers(viewResolvers);
		 resolver.setContentNegotiationManager(manager);
		 
		 return resolver;
	}
	
	/*@Override//metodo que eu inseri para acessar os recursos e funcionou
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	} */
	
	
	//assim fica por conta do spring identificar os arquivos se são css, js, e etc...
	@Override// metodo para configurar a pasta resources indicado pelo professor
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override//metodo que adiciona um interceptador para alterar o locale
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	
	//metodo que pega o locale no cookie
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();//fica salvo em cookie, mas pode configurar para sessão e etc...
	}
	
	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtps.bol.com.br");
		mailSender.setUsername("lucas.vieira.dasilva@bol.com.br");
		mailSender.setPassword("pirituba01");
		mailSender.setPort(587);
		
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);//propriedades para autenticação
		mailProperties.put("mail.smtp.starttls.enable", false);//propriedades para conexao segura TLS
		mailSender.setJavaMailProperties(mailProperties);
		
		return mailSender;
	}
}
