package com.example.shoppingcart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.shoppingcart.dto.ProductDTO;
import com.example.shoppingcart.exception.ItemNotFoundException;
import com.example.shoppingcart.model.Category;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.service.CategoryService;
import com.example.shoppingcart.service.ImageUploadService;
import com.example.shoppingcart.service.ProductService;

@Controller
//@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	CategoryService catservice;
	@Autowired
	ProductService productservice;
	@Autowired
	ImageUploadService imageUploadService;
	public static String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
	@GetMapping("")
	public String adminHome() {
		return "adminHome";
	}
	
	@GetMapping("/categories")
	public String getCategories(Model model) {
		model.addAttribute("categories", catservice.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/categories/add")
	public String getCategoriesAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/categories/add")
	public String postCategoriesAdd(@ModelAttribute("category") Category category) {
		catservice.addCategory(category);
		return "categories";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String delCategory(@PathVariable int id) {
		try {
			catservice.delCategoryById(id);
		}
		catch(Exception e) {
			throw new ItemNotFoundException("id: "+id);
		}
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model) {
		Optional<Category> cat= catservice.getCategoryById(id);
		if (cat.isPresent()) {
			model.addAttribute("category", cat.get());
			return "categoriesAdd";
		}
		return "404";
		//return "redirect:/admin/categories";
	}
	
	@GetMapping("/products")
	public String getProducts(Model model) {
		model.addAttribute("products", productservice.getAllProduct());
		return "products";
	}
	
	@GetMapping("/products/add")
	public String getProductAdd(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", catservice.getAllCategory());
		return "productsAdd";
	}

	@PostMapping("/products/add")
	public String postProductAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file,
			@RequestParam("imgName") String imgName) throws IOException {
		
		Product pro=new Product();
		pro.setId(productDTO.getId());
		pro.setName(productDTO.getName());
		pro.setCategory(catservice.getCategoryById(productDTO.getCategoryId()).get());
		pro.setDescription(productDTO.getDescription());
		pro.setPrice(productDTO.getPrice());
		pro.setWeight(productDTO.getWeight());
		
		String imageUUID="";
		if(!file.isEmpty()) {
			imageUUID=imageUploadService.uploadImage(file);
		}
		else {
			imageUUID = imgName;
		}
		pro.setImageName(imageUUID);
		productservice.addProduct(pro);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/product/delete/{id}")
	public String delProduct(@PathVariable long id) {
		productservice.delProductById(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/product/update/{id}")
	public String updateProduct(@PathVariable long id, Model model) {
		Product product= productservice.getProductById(id).get();
		ProductDTO pdto = new ProductDTO();
		pdto.setId(product.getId());
		pdto.setName(product.getName());
		pdto.setCategoryId(product.getCategory().getId());
		pdto.setDescription(product.getDescription());
		pdto.setPrice(product.getPrice());
		pdto.setWeight(product.getWeight());
		pdto.setImageName(product.getImageName());
		model.addAttribute("productDTO", pdto);
		model.addAttribute("categories", catservice.getAllCategory());
		
		return "productsAdd";
	}
}
