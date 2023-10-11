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
import org.springframework.web.bind.annotation.RestController;

import com.gaftech.blog.constants.AppConstants;
import com.gaftech.blog.payloads.ApiResponse;
import com.gaftech.blog.payloads.CategoryDto;
import com.gaftech.blog.services.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * @author Rutik
	 * @apiNote save category data
	 * @param categoryDto
	 * @return 
	 * @since 1.0v
	 */
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		
		log.info("Entering the request for save category data");
		
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);

		log.info("Completed the request for save category data");
		
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	/**
	 * @author Rutik
	 * @apiNote update category
	 * @param categoryDto
	 * @param catId
	 * @return
	 * @since 1.0v
	 * 
	 */
	@PutMapping("/update/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId) {
		
		log.info("Entering the request for update the category data with categoryId : {}",catId);
		
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);

		log.info("Completed the request for update the category data with categoryId : {}",catId);
		
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}

	/**
	 * @author Rutik
	 * @apiNote delete user by catId
	 * @param catId
	 * @return 
	 * @since 1.0v
	 */
	@DeleteMapping("/delete/{catId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer catId) {
		
		log.info("Entering the request for delete the category data with categoryId : {}",catId);
		
		this.categoryService.deleteCategory(catId);
		
		log.info("Completed the request for delete the category data with categoryId : {}",catId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.DELETED, true),
				HttpStatus.OK);
	}

	/**
	 * @author Rutik
	 * @apiNote get single category by catId
	 * @param catId
	 * @return 
	 * @since 1.0v
	 */
	@GetMapping("/singlecategory/{catId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer catId) {
		
		log.info("Entering the request for get category data with categoryId : {}",catId);
		
		CategoryDto categoryDto = this.categoryService.getCategory(catId);

		log.info("Completed the request for get category data with categoryId : {}",catId);
		
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	/**
	 * @author Rutik
	 * @apiNote get all categories
	 * @return  
	 * @since 1.0v
	 */
	@GetMapping("/allcategories")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		
		log.info("Entering the request for get all category data");
		
		List<CategoryDto> categories = this.categoryService.getCategories();
		
		log.info("Completed the request for get all category data");
		
		return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);
	}
}
