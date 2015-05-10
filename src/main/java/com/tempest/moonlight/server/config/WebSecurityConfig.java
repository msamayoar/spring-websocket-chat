package com.tempest.moonlight.server.config;

import com.tempest.moonlight.server.repository.persistance.dao.UserDAO;
//import com.tempest.moonlight.server.security.CustomAuthenticationManager;
import com.tempest.moonlight.server.repository.persistance.dao.UserMockDAOImpl;
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



	//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.authenticationProvider(new AuthenticationProvider() {
//
//			@Override
//			public boolean supports(Class<?> authentication) {
//				return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//			}
//
//			@Override
//			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
////				UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
////
////				List<GrantedAuthority> authorities = SECURE_ADMIN_PASSWORD.equals(token.getCredentials()) ?
////						AuthorityUtils.createAuthorityList("ROLE_ADMIN") : null;
////
////
////				return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
//
//				Assert.isTrue(!authentication.isAuthenticated(), "Already authenticated");
//				String key = authentication.getPrincipal().toString();
//				if (!StringUtils.hasText(key)) {
//					throw new InternalAuthenticationServiceException("User key must not be empty.");
//				}
//				if (!userDAO.exists(key)) {
//					throw new InternalAuthenticationServiceException("User does not exist in database.");
//				}
//				UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//						token.getPrincipal(),
//						token.getPrincipal(),
//						Arrays.asList(new SimpleGrantedAuthority("USER"))
//				);
//				return auth;
//			}
//		});
//	}
}
