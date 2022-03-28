package com.alkemy.ong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;

	@PutMapping("/{id}")//OT169-43
	//@Secured("ROLE_ADMIN")
	public ResponseEntity<Category> updateCategory(@RequestParam (name = "id") String id, // I get the ID
			@RequestBody Category category){ //I get the Category to be updated
		System.out.println("Update");
		if(categoryRepository.existsById(id)) {//If the category exists			
			return new ResponseEntity<Category>(categoryService.save(category), HttpStatus.OK); //I update it. 
		}
		//If it doesn't exists, then I return a 500 error code
		return new ResponseEntity<Category>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
