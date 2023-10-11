package com.gaftech.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gaftech.blog.constants.AppConstants;
import com.gaftech.blog.entities.Category;
import com.gaftech.blog.entities.Post;
import com.gaftech.blog.entities.User;
import com.gaftech.blog.exceptions.ResourceNotFoundException;
import com.gaftech.blog.payloads.PostDto;
import com.gaftech.blog.payloads.PostResponse;
import com.gaftech.blog.repositories.CategoryRepository;
import com.gaftech.blog.repositories.PostRepository;
import com.gaftech.blog.repositories.UserRepository;
import com.gaftech.blog.services.PostService;

@Service
public class PostServiceImple implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	Logger logger = LoggerFactory.getLogger(PostServiceImple.class); 
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		logger.info("Entering dao class for save post with userId : {} and categoryId : {}",userId, categoryId);
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));

		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post updatePost = this.postRepository.save(post);
		
		PostDto updatePostDto = this.modelMapper.map(updatePost, PostDto.class);
		
		logger.info("Completed dao class for save post with userId : {} and categoryId : {}",userId, categoryId);
		
		return updatePostDto;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		logger.info("Entering dao call for update post data with postId : {} ",postId);
		
		Post post = this.postRepository.findById(postId)
		.orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+postId));
		
		post.setPostTitle(postDto.getTitle());
		post.setPostContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepository.save(post);
		
		logger.info("Completed dao call for update post data with postId : {} ",postId);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		logger.info("Entering dao call for delete post data with postId : {}"+postId);
		
		Post post = this.postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+postId));
		
		this.postRepository.delete(post);
		
		logger.info("Completed dao call for delete post data with postId : {}"+postId);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort );
		
		logger.info("Entering dao call for get all post data");
		
		Page<Post> pagePost = this.postRepository.findAll(p);
		
		List<Post> allPosts = pagePost.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		logger.info("Completed dao call for get all post data");
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		logger.info("Entering dao call for get post data with postId : {}",postId);
		
		Post post = this.postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+postId));

		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		
		logger.info("Completed dao call for get post data with postId : {}",postId);
		
		return postDto;
		
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {

		logger.info("Entering dao call for get post by category with categoryId : {}", categoryId);
		
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));
		List<Post> posts = this.postRepository.findByCategory(cat);
		
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		logger.info("Completed dao call for get post by category with categoryId : {}", categoryId);
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		logger.info("Entering dao call for get post data by user with userId : {} ",userId);
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));
		List<Post> posts = this.postRepository.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		logger.info("Completed dao call for get post data by user with userId : {} ",userId);
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		
		logger.info("Entering dao call for search post title data with search keywords : {}",keyword);
		
		List<Post> posts = this.postRepository.findByPostTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		logger.info("Completed dao call for search post title data with search keywords : {}",keyword);
		
		return postDtos;
	}

}
