package com.gaftech.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaftech.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	
}
