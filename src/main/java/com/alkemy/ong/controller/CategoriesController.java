package com.alkemy.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.entity.Category;
import com.alkemy.ong.repository.CategoryRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.CategoryDTO;

import com.alkemy.ong.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")//OT169-42
	@PreAuthorize("hasRole('ROLE_ADMIN')") // This method only permits the current role to enter this endpoint
	public ResponseEntity<Category> createCategory(@RequestBody Category category){ // I get the entity
		System.out.println("Create");
		return new ResponseEntity<Category>(categoryService.save(category), HttpStatus.OK); //if it doesn't had errors, return it with a 200 code.
		
	}	
	
	@GetMapping("/{id}") //OT169-41
	@PreAuthorize("hasRole('ROLE_ADMIN')") // This method only permits the current role to enter this endpoint
	public ResponseEntity<Category> getById(@RequestParam (name = "id", required=false) String id) {
		System.out.println("Get by id");
		System.out.println("ID="+id);
		System.out.println(categoryRepository.existsById(id));
		if(categoryRepository.existsById(id)){//If the ID corresponds to an Category, returns it
			return new ResponseEntity<Category>(categoryService.getById(id), HttpStatus.OK);
		}
		//If the ID doesn't corresponds to an Category, sends an error
		return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
				
	}

	/**
	 * @author Franco Lamberti
	 * This method is only allowed to Admins. Returns all the names of the categories.
	 */
	@GetMapping("/")// OT169-40
	@PreAuthorize("hasRole('ROLE_ADMIN')") // This method only permits the current role to enter this endpoint
	public List<CategoryDTO> getNamesFromAll(){
		System.out.println("Get all");
		return categoryService.getAllCategories();
	}	

}
