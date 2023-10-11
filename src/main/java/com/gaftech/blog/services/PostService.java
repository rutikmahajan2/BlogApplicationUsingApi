package com.gaftech.blog.services;

import java.util.List;
import com.gaftech.blog.payloads.PostDto;
import com.gaftech.blog.payloads.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

	// get single post
	PostDto getPostById(Integer postId);

	// get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);

	// get all post by user
	List<PostDto> getPostByUser(Integer userId);
	
	// search posts
	List<PostDto> searchPost(String keyword);
}
