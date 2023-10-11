package com.gaftech.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaftech.blog.entities.Category;
import com.gaftech.blog.entities.Post;
import com.gaftech.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByPostTitleContaining(String title);
}
