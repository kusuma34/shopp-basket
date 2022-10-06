package com.example.shoppingcart.cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.example.shoppingcart.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	AuthenticationConfiguration authenticationConfiguration;

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


		http
			.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/register","/admin/**", "/oauth2/**","/shop/**", "/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/addToCart/**", "/cart/**").permitAll().anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
//			.loginProcessingUrl("/login")
			.failureUrl("/login?error=true")
			.defaultSuccessUrl("/shop")
			.usernameParameter("email")
			.passwordParameter("password")
			
			.and()
			.oauth2Login()
			.loginPage("/login")
			.successHandler(googleOAuth2SuccessHandler)
			.and()

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID").and()
				.exceptionHandling();
		return http.build();
	}

	@Bean
	protected BCryptPasswordEncoder passwordEcnrypter() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEcnrypter());
	}

	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**",
				"/js/**");
		// .antMatchers(HttpMethod.OPTIONS, "http://localhost:8080/**");
	}

	@Bean
	AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
