package com.example.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.shoppingcart.model.Role;
import com.example.shoppingcart.model.User;
import com.example.shoppingcart.repository.RoleRepository;

@SpringBootApplication
public class ShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	@Configuration
    public class WebConfig implements WebMvcConfigurer {      
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/productImages/","classpath:/static/images/", "classpath:/static/")
            .setCachePeriod(0);
        }
    }
	
	@Autowired
    private RoleRepository repository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
    	try {
            repository.save(new Role("ROLE_ADMIN", new ArrayList<User>()));
            repository.save(new Role("ROLE_USER", new ArrayList<User>()));	
    	}catch(Exception e) {}
    }
}
