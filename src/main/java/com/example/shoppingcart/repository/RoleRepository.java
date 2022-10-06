package com.example.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppingcart.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
