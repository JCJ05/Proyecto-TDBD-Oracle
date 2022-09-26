package web.proyecto.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import web.proyecto.oracle.models.service.JpaUsuariosService;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private JpaUsuariosService userDetailsService;
     
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		   
		
		  http.authorizeRequests().antMatchers("/", "/index","/css/**","/boxicons/**","/bsfonts/**","/css2/**","/fonts/**","/images/**","/js/**","/js2/**" ,"/usuario/form","/denuncia/informacion" , "/denuncia/form/**" 
				  ,"/publicacion/lista","/publicacion/info/**","/denuncia/agradecimiento","/evento/listaEventos","/evento/info/**","/uploads/**" ,"/publicacion/filtroLong/**" , "/publicacion/filtro","/publicacion/filtroFecha/**"
				  ,"/publicacion/filtroTotal/**" , "/eventoVoluntario/inscribirse/**" , "/conocenos")
		  .permitAll()
		  .anyRequest().authenticated()
		  .and().formLogin().loginPage("/login")
		  .permitAll().and().logout().permitAll()
		  .and().exceptionHandling().accessDeniedPage("/error_403");
	    	
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		
		builder.userDetailsService(userDetailsService);
		
		
	}

    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	

	
	
	
	
	
}
