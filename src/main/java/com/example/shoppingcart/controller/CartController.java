package com.example.shoppingcart.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.shoppingcart.exception.ItemNotFoundException;
import com.example.shoppingcart.global.GlobalData;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.service.ProductService;


@Controller
//@RestController
@Transactional
//@CrossOrigin(origins = "http://localhost:8080/**")
public class CartController {
	@Autowired
	ProductService proservice;
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable long id) {
		GlobalData.cart.add(proservice.getProductById(id).get());
		return "redirect:/shop";
	}
	
	@GetMapping("/cart")
	public String getCart(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", GlobalData.cart);
		return "cart";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String removeItemInCart(@PathVariable int index) {
		try {
			GlobalData.cart.remove(index);
		}
		catch(Exception e) {
			throw new ItemNotFoundException("index: "+index);
		}
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}


}
