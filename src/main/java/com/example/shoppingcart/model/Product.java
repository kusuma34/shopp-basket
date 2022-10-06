package com.example.shoppingcart.model;

import javax.persistence.*;


@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category;
	private double price;
	private double weight;
	private String description;
	private String ImageName;
	
	
	public Product() {
		super();
	}
	public Product(String name, Category category, double price, double weight, String description, String imageName) {
		super();
		this.name = name;
		this.category = category;
		this.price = price;
		this.weight = weight;
		this.description = description;
		ImageName = imageName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageName() {
		return ImageName;
	}
	public void setImageName(String imageName) {
		ImageName = imageName;
	}
	

}
