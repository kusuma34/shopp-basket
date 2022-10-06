package com.example.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppingcart.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
