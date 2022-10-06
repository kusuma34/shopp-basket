package com.example.shoppingcart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppingcart.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);

}
