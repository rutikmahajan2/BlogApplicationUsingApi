package com.gaftech.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaftech.blog.constants.AppConstants;
import com.gaftech.blog.entities.Category;
import com.gaftech.blog.exceptions.ResourceNotFoundException;
import com.gaftech.blog.payloads.CategoryDto;
import com.gaftech.blog.repositories.CategoryRepository;
import com.gaftech.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		logger.info("Entering dao call for save the category data");
		
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		
		Category addedCat = this.categoryRepository.save(cat);
		
		logger.info("Completed dao call for save the category data");
		
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		logger.info("Entering dao call for update category data with categoryId : {}",categoryId);
		
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCat = this.categoryRepository.save(cat);
		
		logger.info("Completed dao call for update category data with categoryId : {}",categoryId);
		
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		logger.info("Entering dao call for delete category data with categoryId : {}",categoryId);
		
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));
		
		logger.info("Completed dao call for delete category data with categoryId : {}",categoryId);
		
		this.categoryRepository.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryID) {

		logger.info("Entering dao call for get the category data with categoryId : {}",categoryID);
		
		Category cat = this.categoryRepository.findById(categoryID)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryID));
		
		logger.info("Completed dao call for get the category data with categoryId : {}",categoryID);
		
		return this.modelMapper.map(cat, CategoryDto.class);	
	}

	@Override
	public List<CategoryDto> getCategories() {

		logger.info("Entering dao call for get all category data");
		
		List<Category> categories = this.categoryRepository.findAll();
		
		List<CategoryDto> catDtos = categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		
		logger.info("Completed dao call for get all category data");
		
		return catDtos;
	}

	
}
