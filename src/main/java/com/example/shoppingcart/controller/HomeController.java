package com.example.shoppingcart.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.shoppingcart.global.GlobalData;
import com.example.shoppingcart.service.CategoryService;
import com.example.shoppingcart.service.ProductService;


@Controller
//@RestController
@Transactional
public class HomeController {
	@Autowired
	CategoryService catservice;
	@Autowired
	ProductService proservice;
	
	@GetMapping({"/","/home"})
	public String home(Model model) {
	        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        Boolean logged = null != authentication && !("anonymousUser").equals(authentication.getName());
	        System.out.println("------------"+logged+"-------------------");
	    
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories", catservice.getAllCategory());
		model.addAttribute("products", proservice.getAllProduct());
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", catservice.getAllCategory());
		model.addAttribute("products", proservice.getAllProductByCategoryId(id));
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable long id) {
		model.addAttribute("product", proservice.getProductById(id).get());
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "viewProduct";
	}

}
