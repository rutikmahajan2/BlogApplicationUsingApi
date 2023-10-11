 package com.gaftech.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaftech.blog.constants.AppConstants;
import com.gaftech.blog.payloads.ApiResponse;
import com.gaftech.blog.payloads.PostDto;
import com.gaftech.blog.payloads.PostResponse;
import com.gaftech.blog.services.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	//create
	/**
	 * @author Rutik
	 * @apiNote save post data
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return 
	 * @since 1.0v 
	 */
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@Valid
			@RequestBody PostDto postDto,
			@PathVariable Integer userId, 
			@PathVariable Integer categoryId)
	{
		
		log.info("Entering the request for create a post with userId : {} and categoryId : {}",userId, categoryId);
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		log.info("Completed the request for create a post with userId : {} and categoryId : {}",userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}

	// get by user
	/**
	 * @author Rutik
	 * @apiNote get posts by user id
	 * @param userId
	 * @return 
	 * @since 1.0v
	 */
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		log.info("Entering the request for get post data by User with userId : {}",userId);
		
		List<PostDto> posts = this.postService.getPostByUser(userId);
		
		log.info("Completed the request for get post data by User with userId : {}",userId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// get by category
	/**
	 * @author Rutik
	 * @apiNote get posts by category id
	 * @param categoryId
	 * @return 
	 * @since 1.0v
	 */
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		
		log.info("Entering the request for get post data by category with categoryId : {} ", categoryId);
		
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		
		log.info("Completed the request for get post data by category with categoryId : {} ", categoryId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// get all post
	/**
	 * @author Rutik
	 * @apiNote get all post
	 * @return 
	 * @since 1.0v
	 */
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam (value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam (value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			){
		
		log.info("Entering the request for get all post data");
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		
		log.info("Completed the request for get all post data");
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		 
	}
	
	// get post details by id
	
	/**
	 * @author Rutik
	 * @apiNote get all post
	 * @param postId
	 * @return 
	 * @since 1.0v
	 */
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable Integer postId){
		
		log.info("Entering the request for get post data with postId : {}",postId);
		
		PostDto postDto = this.postService.getPostById(postId);
		
		log.info("Completed the request for get post data with postId : {}",postId);
		
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
		
	}
	
	// delete post by postId
	/**
	 * @author Rutik
	 * @apiNote delete post by id
	 * @param postId
	 * @return
	 * @since 1.0v
	 */
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		
		log.info("Entering the request for delete post data with postId : {}",postId);
		
		this.postService.deletePost(postId);
		
		log.info("Completed the request for delete post data with postId : {}",postId);
		
		return new ResponseEntity<>(new ApiResponse(AppConstants.DELETED, true), HttpStatus.OK);
	}
	
	// update post by postId
	/**
	 * @author Rutik
	 * @apiNote update post by id
	 * @param postDto
	 * @param postId
	 * @return
	 * @since 1.0v
	 */
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId){
		
		log.info("Entering the request for update post data with postId : {}",postId);
		
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		log.info("Completed the request for update post data with postId : {}",postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK); 
		
		
	}
	
	// search 
	/**
	 * @author Rutik
	 * @apiNote search post by title using keywords
	 * @param keywords
	 * @return
	 * @since 1.0v
	 */
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keywords") String keywords
			){
		
		log.info("Entering the request for search post data by titel with keywords : {}",keywords);
		
		List<PostDto> result = this.postService.searchPost(keywords);
		
		log.info("Completed the request for search post data by titel with keywords : {}",keywords);
		
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}
}
