package com.tempest.moonlight.server.config;

import com.tempest.moonlight.server.repository.persistence.dao.UserDAO;
//import com.tempest.moonlight.server.security.CustomAuthenticationManager;
import com.tempest.moonlight.server.repository.persistence.dao.UserMockDAOImpl;
import com.tempest.moonlight.server.security.CustomAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
//@EnableRedisHttpSession
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String SECURE_ADMIN_PASSWORD = "rockandroll";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.formLogin()
				.loginPage("/index.html")
				.defaultSuccessUrl("/chat.html")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/index.html")
				.permitAll()
				.and()
			.authorizeRequests()
				.antMatchers("/js/**", "/lib/**", "/images/**", "/css/**", "/index.html", "/").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/stats").hasRole("ADMIN")
				.anyRequest().authenticated();
				
	}

//	@Bean
//	public AuthenticationManager authenticationManager() {
//		return new CustomAuthenticationManager();
//	}

//	@Autowired
//	private UserDAO userDAO;

	@Bean
	protected UserDAO userDAO() {
		return new UserMockDAOImpl();
	}

//	@Override
//	@Bean
//	protected AuthenticationManager authenticationManager() throws Exception {
//		return new CustomAuthenticationManager(userDAO());
//	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return new CustomAuthenticationManager(/*userDAO()*/);
	}
}
