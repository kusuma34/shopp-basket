package com.example.shoppingcart.cofig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.shoppingcart.model.Role;
import com.example.shoppingcart.model.User;
import com.example.shoppingcart.repository.RoleRepository;
import com.example.shoppingcart.repository.UserRepository;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository userrepo;
	@Autowired
	RoleRepository rolerepo;
	private RedirectStrategy redirectinternally = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();
		if(userrepo.findByEmail(email).isPresent()) {
			
		}
		else {
			User user = new User();
			user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
			user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
			user.setEmail(email);
			List<Role> roles = new ArrayList<>();
			roles.add(rolerepo.findById(2).get());
			user.setRoles(roles);
			userrepo.save(user);
		}
		redirectinternally.sendRedirect(request, response, "/shop");
		
	}
}
