package com.alkemy.ong.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	@Autowired
	public CategoryService(CategoryRepository categoryRepository){
		this.categoryRepository = categoryRepository;
	}
	
	private CategoryDTO convertEntityToDto(Category category) {

		return new CategoryDTO(
				category.getId(),
				category.getDescription(),
				category.getImage(),
				category.getName()
		);
	}
	
	//Get all
	public List<CategoryDTO> getAllCategories(){
		return categoryRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	//Gey names all
	public List<CategoryDTO> getNamesFromAllCategories(){
		return categoryRepository.getNamesFromAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}
	//Get by id
	public Category getById(String id){
		return categoryRepository.getById(id);
	}
	//Create and update
	public Category save(Category category) {
		return categoryRepository.save(category);
	}	
	//Delete
	public ResponseEntity<Category> delete(Category category) {
		categoryRepository.delete(category);
		return new ResponseEntity<Category>(HttpStatus.OK);
	}
	
	public List<String> getNamesFromAll(){
		return categoryRepository.findAll()
				.stream()
				.map(Category::getName)
				.collect(Collectors.toList());
	}

	public boolean existsById(String id){
		return categoryRepository.existsById(id);
	}

	public Map<String, Object> getAllPages (Integer page) throws Exception {
		Integer size = 10;
		try {
			List<List<LinkedHashMap>> categoriesList = new ArrayList<List<LinkedHashMap>>();

			Pageable paging = PageRequest.of(page, size);
			String url = "/categories/pages?page=";

			Page<List<LinkedHashMap>> pagedCategories;
			pagedCategories = categoryRepository.findPage(paging);

			categoriesList = pagedCategories.getContent();
			Map<String, Object> response = new LinkedHashMap<>();
			response.put("Total Items", pagedCategories.getTotalElements());
			response.put("Total Pages", pagedCategories.getTotalPages());
			response.put("Current Page", pagedCategories.getNumber());

			if (pagedCategories.getNumber() == pagedCategories.getTotalPages() - 1) {
				response.put("Next Page", "This is the last page");
			} else {
				response.put("Next Page", url.concat(String.valueOf(pagedCategories.getNumber() + 1)));
			}
			if (pagedCategories.getNumber() == 0) {
				response.put("Previous Page", "This is the first page");
			} else {
				response.put("Previous Page", url.concat(String.valueOf(pagedCategories.getNumber() - 1)));
			}
			response.put("Categories", categoriesList);

			return response;

		} catch (Exception e) {
			throw new Exception("Fail to load pages");
		}
	}
	
}
