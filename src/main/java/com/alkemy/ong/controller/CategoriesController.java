package com.alkemy.ong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.entity.Category;
import com.alkemy.ong.service.CategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@Api(tags = "Categories Controller")
public class CategoriesController {
	private final CategoryService categoryService;
	@Autowired
	public CategoriesController(CategoryService categoryService){
		this.categoryService = categoryService;
	}


	@GetMapping("/")//OT169-40
	@ApiOperation(value = "Selects all the names from the categories" , consumes = "application/json")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Returns all the names (if there aren't categories created already, it returns an empty list)"),
			@ApiResponse(code = 401, message = "There aren't authorization headers"),
			@ApiResponse(code = 403, message = "Error, the user doesn't have the permissions to use this method"),
			@ApiResponse(code = 404, message = "Error, not found any Category.class")
	})
	public List<String> getNamesFromAll(){
		return categoryService.getNamesFromAll();
	}

	@ApiOperation(value = "Select only one category, found by ID" , consumes = "application/json")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Returns the entire category with the previous ID"),
			@ApiResponse(code = 401, message = "There aren't authorization headers"),
			@ApiResponse(code = 403, message = "Error, the user doesn't have the permissions to use this method"),
			@ApiResponse(code = 404, message = "Error, not found any Category with that ID")
	})
	@GetMapping("/{id}") // OT169-41
	public ResponseEntity<Category> getById(@PathVariable(name = "id", required = false) String id) {
		if (categoryService.existsById(id)) {// If the ID corresponds to an Category, returns it
			return new ResponseEntity<Category>(categoryService.getById(id), HttpStatus.OK);
		}
		// If the ID doesn't corresponds to an Category, sends an error
		return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Creates a category", consumes = "application/json")
	@ApiResponses( value = {
			@ApiResponse(code = 201, message = "Returns the entire created category"),
			@ApiResponse(code = 401, message = "There aren't authorization headers"),
			@ApiResponse(code = 403, message = "Error, the user doesn't have the permissions to use this method"),
			@ApiResponse(code = 404, message = "Error, not found any Category with that ID")
	})
	@PostMapping("/") // OT169-42
	public ResponseEntity<Category> createCategory(@RequestBody Category category) { // I get the entity
		return new ResponseEntity<Category>(categoryService.save(category), HttpStatus.CREATED); // if it doesn't had errors,
	}

	@ApiOperation(value = "Updates a category", consumes = "application/json")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Returns the entire updated category"),
			@ApiResponse(code = 401, message = "There aren't authorization headers"),
			@ApiResponse(code = 403, message = "Error, the user doesn't have the permissions to use this method"),
			@ApiResponse(code = 404, message = "Error, not found any Category with that ID")
	})
	@PutMapping("/{id}") // OT169-43
	public ResponseEntity<Category> updateCategory(@PathVariable(name = "id") String id, // I get the ID
												   @RequestBody Category category) { // I get the Category to be updated
		if (categoryService.existsById(id)) {// If the category exists
			return new ResponseEntity<Category>(categoryService.save(category), HttpStatus.OK); // I update it.
		}
		// If it doesn't exists, then I return a 500 error code
		return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Deletes a category", consumes = "application/json")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Doesn't return anything, deletes the category with the ID sent"),
			@ApiResponse(code = 401, message = "There aren't authorization headers"),
			@ApiResponse(code = 403, message = "Error, the user doesn't have the permissions to use this method"),
			@ApiResponse(code = 404, message = "Error, not found any Category with that ID")
	})
	@DeleteMapping("/{id}") // OT169-44
	public ResponseEntity<Category> deleteCategory(@PathVariable(name = "id") String id) { // I get the Category to be deleted
		if (categoryService.existsById(id)) {// If the category exists
			categoryService.delete( categoryService.getById(id) );// I delete it
			return new ResponseEntity<Category>(HttpStatus.OK); // I return a 200 code
		}
		// If it doesn't exists, then I return a 500 error code
		return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Gets the pagination of Categories", consumes = "application/json")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Returns the pagination of all the categories"),
			@ApiResponse(code = 401, message = "There aren't authorization headers"),			
			@ApiResponse(code = 404, message = "Error, not found any Page with that Integer")
	})
	@GetMapping("/pages")
	public ResponseEntity<Map<String, Object>> getAllPage( @RequestParam(defaultValue = "0") int page) {
		Map<String, Object> cate = new HashMap<>();
		try {
			cate = categoryService.getAllPages(page);
			return new ResponseEntity<>(cate, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
