package com.example.shoppingcart.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.shoppingcart.model.User;
import com.example.shoppingcart.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository userrepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userrepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role ->{
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities); 
	}

}
