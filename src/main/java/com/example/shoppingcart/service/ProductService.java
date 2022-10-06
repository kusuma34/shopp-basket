package com.example.shoppingcart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productrepo;

	public List<Product> getAllProduct(){
		return productrepo.findAll();
	}
	
	public void addProduct(Product product) {
		productrepo.save(product);
	}
	
	public void delProductById(long id) {
		productrepo.deleteById(id);
	}
	
	public Optional<Product> getProductById(long id) {
		return productrepo.findById(id);
	}
	
	public List<Product> getAllProductByCategoryId(int id){
		return productrepo.findAllByCategory_Id(id);
	}
}
