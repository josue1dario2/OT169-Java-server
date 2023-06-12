package com.alkemy.ong.controller;

import com.alkemy.ong.entity.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.impl.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoriesControllerTest {

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    ObjectMapper mapper = new ObjectMapper();

    //Get names from all endpoint tests
    @Test
    @DisplayName("Get the names from all Categories with role Admin: Success (Code 200 OK)")
    void getNamesFromAll__Success_RoleAdmin() throws Exception {
        //I create the categories I will use in this test
        Category c1 = new Category("1", "Category1", "Description", null, null, false);
        Category c2 = new Category("2", "Category2", "Description", null, null, false);
        Category c3 = new Category("3", "Category3", "Description", null, null, false);

        //I create the list that has the names of the Categories created
        List<String> names = new ArrayList<String>();
        names.add(c1.getName());
        names.add(c2.getName());
        names.add(c3.getName());

        //When I use GetNamesFromAll it returns the actual names of the entities created
        Mockito.when(categoryService.getNamesFromAll()).thenReturn( names );//I return the category when the ID is "1"

        //I create a list with the expected names that I want to have in the actual list
        List<String> expected = new ArrayList<String>();
        expected.add("Category1");
        expected.add("Category2");
        expected.add("Category3");

        //I realize a request to the server with admin Role
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/")
                .with(user("admin").roles("ADMIN"))
        )
                .andExpect(status().isOk()) // I verify that the code is 200
                .andExpect(jsonPath("$").isNotEmpty() ); // I verify that the list isn't empty
        assertEquals(expected, names);// I verify that the expected names are the obtained names from the method
    }

    @Test
    @DisplayName("Get the names from all Categories with Role User: Success (Code 200 OK)")
    void getNamesFromAll__Success_RoleUser() throws Exception {
        //I create the categories I will use in this test
        Category c1 = new Category("1", "Category1", "Description", null, null, false);
        Category c2 = new Category("2", "Category2", "Description", null, null, false);
        Category c3 = new Category("3", "Category3", "Description", null, null, false);

        //I create the list that has the names of the Categories created
        List<String> names = new ArrayList<String>();
        names.add(c1.getName());
        names.add(c2.getName());
        names.add(c3.getName());

        //When I use GetNamesFromAll it returns the actual names of the entities created
        Mockito.when(categoryService.getNamesFromAll()).thenReturn( names );//I return the category when the ID is "1"

        //I create a list with the expected names that I want to have in the actual list
        List<String> expected = new ArrayList<String>();
        expected.add("Category1");
        expected.add("Category2");
        expected.add("Category3");

        //I realize a request to the server with user Role
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/")
                        .with(user("user").roles("USER"))
                )
                .andExpect(status().isOk()) // I verify that the code is 200
                .andExpect(jsonPath("$").isNotEmpty() ); // I verify that the list isn't empty
        assertEquals(expected, names);// I verify that the expected names are the obtained names from the method
    }

    @Test
    @DisplayName("Attempt to get the names from all Categories: Error (Code 401 Unauthorized)")
    void getNamesFromAll__isUnauthorized() throws Exception {
        //I create the categories I will use in this test
        Category c1 = new Category("1", "Category1", "Description", null, null, false);
        Category c2 = new Category("2", "Category2", "Description", null, null, false);
        Category c3 = new Category("3", "Category3", "Description", null, null, false);

        //I create the list that has the names of the Categories created
        List<String> names = new ArrayList<String>();
        names.add(c1.getName());
        names.add(c2.getName());
        names.add(c3.getName());

        //When I use GetNamesFromAll it returns the actual names of the entities created
        Mockito.when(categoryService.getNamesFromAll()).thenReturn( names );

        //I create a list with the expected names that I want to have in the actual list
        List<String> expected = new ArrayList<String>();
        expected.add("Category1");
        expected.add("Category2");
        expected.add("Category3");

        //I realize a request  to the server without roles
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/"))
                .andExpect(status().isUnauthorized());//I throw a 401 error
        assertEquals(expected, names);// I verify that the expected names are the obtained names from the method
    }
    //Get names from all endpoint tests

    //Get by Id endpoint tests
    @Test
    @DisplayName("Get an Category by id: Success (Code 200 OK)")
    void getById__GetCorrectEntity() throws Exception {
        //I create the category that I will use
        Category category = new Category("1", "Category1", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.getById( category.getId() )).thenReturn(category);
        Mockito.when(categoryService.existsById( category.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.getById( category.getId() )).thenReturn(category);
        Mockito.when(categoryRepository.existsById( category.getId() )).thenReturn(true);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/"+ category.getId() )//I send the ID in the URL
                .with(user("admin").roles("ADMIN"))
        ).andExpect(status().isOk());//I verify that the code is 200

        assertEquals(categoryService.getById( category.getId() ), category);//I verify that if  I send the ID of a category, it returns me that category with the specified ID
    }

    @Test
    @DisplayName("Attempt to get a category with an Unauthorized user: Error (Code 401 Unauthorized)")
    void getById__isUnauthorized() throws Exception {
        //I create the category that I will use
        Category category = new Category("1", "Category1", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.getById( category.getId() )).thenReturn(category);
        Mockito.when(categoryRepository.getById( category.getId() )).thenReturn(category);

        //I realize a request to the server without any role
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/"+ 2 )
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Attempt to get a category with an forbidden user: Error (Code 403 Forbidden)")
    void getById__isForbidden() throws Exception {
        //I create the category that I will use
        Category category = new Category("1", "Category1", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.getById( category.getId() )).thenReturn(category);
        Mockito.when(categoryRepository.getById( category.getId() )).thenReturn(category);

        //I realize a request to the server with role User
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/"+ 2 )
                .with(user("user").roles("USER"))
        ).andExpect(status().isForbidden());//I verify that the code is 403
    }

    @Test
    @DisplayName("Attempt to get a category by wrong id: Error (Code 404 Not Found)")
    void getById__GetIncorrectEntity() throws Exception {
        //I create the category that I will use
        Category category = new Category("1", "Category1", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.getById( category.getId() )).thenReturn(category);
        Mockito.when(categoryRepository.getById( category.getId() )).thenReturn(category);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/"+ 2 )//I send an ID that doesn't exists in any category
                .with(user("admin").roles("ADMIN"))
        ).andExpect(status().isNotFound());//I verify that the code is 404

        assertNotEquals(categoryService.getById( "2" ), category);//I verify that the Category and the GetById sent are not the same entity
    }
    //Get by Id endpoint tests

    //Create category Endpoint tests
    @Test
    @DisplayName("Creating a Category: Success (Code 201 Created)")
    void createCategory__MethodIsOK_AdminRole() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Created Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(c1);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.post("/categories/")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(c1))//I put the values for the entity that I will create
                .with(user("admin").roles("ADMIN"))
        ).andExpect(status().isCreated());//I verify that the code is 201
    }

    @Test
    @DisplayName("Attempt to create a Category: Error (Code 401 Unauthorized)")
    void createCategory__MethodIsOK_UserRole() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Created Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(c1);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.post("/categories/")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))//I put the values for the entity that I will create
                .with(user("user").roles("USER"))
        ).andExpect(status().isCreated());//I verify that the code is 201
    }

    @Test
    @DisplayName("Attempt to create a Category: Error (Code 401 Unauthorized)")
    void createCategory__isUnauthorized() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Created Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(c1);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.post("/categories/")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))//I put the values for the entity that I will create
        ).andExpect(status().isUnauthorized());//I verify that the code is 401
    }
    //Create category Endpoint tests

    //Update Category Endpoint tests
    @Test
    @DisplayName("Updating a Category: Success (Code 200 Ok)")
    void updateCategory__MethodIsOK() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Updated Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryService.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryService.getById( c1.getId() )).thenReturn(c1);
        Mockito.when(categoryRepository.getById( c1.getId() )).thenReturn(c1);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+ c1.getId() )
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))//I put the values for the entity that I will create
                .with(user("admin").roles("ADMIN"))
        ).andExpect(status().isOk());//I verify that the code is 200
    }

    @Test
    @DisplayName("Attempt to update a Category, unauthorized User: Error (Code 401 Unauthorized)")
    void updateCategory__isUnauthorized() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Updated Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryService.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryService.getById( c1.getId() )).thenReturn(c1);
        Mockito.when(categoryRepository.getById( c1.getId() )).thenReturn(c1);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+ c1.getId() )//I send the correct ID
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))//I put the values for the entity that I will create
        ).andExpect(status().isUnauthorized());//I verify that the code is 401
    }

    @Test
    @DisplayName("Attempt to update a Category, forbidden User: Error (Code 403 Forbidden)")
    void updateCategory__isForbidden() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Updated Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryService.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryService.getById( c1.getId() )).thenReturn(c1);
        Mockito.when(categoryRepository.getById( c1.getId() )).thenReturn(c1);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+ c1.getId() )//I send the correct ID
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))//I put the values for the entity that I will create
                .with(user("user").roles("USER"))
        ).andExpect(status().isForbidden());//I verify that the code is 403
    }

    @Test
    @DisplayName("Attempt to update a Category, wrong ID: Error (Code 404 Not Found)")
    void updateCategory__isNotFound() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Updated Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryService.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(c1);
        Mockito.when(categoryService.getById( c1.getId() )).thenReturn(c1);
        Mockito.when(categoryRepository.getById( c1.getId() )).thenReturn(c1);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+ 2 )//I send an incorrect ID
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))
                .with(user("admin").roles("ADMIN"))
        ).andExpect(status().isNotFound());//I verify that the code is 404
    }
    //Update Category Endpoint tests

    //Delete Category Endpoint tests
    @Test
    @DisplayName("Deleting a Category: Success (Code 200 Ok)")
    void deleteCategory__DeleteCorrectEntity() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Created Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryService.delete(any(Category.class))).thenReturn(null);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/"+c1.getId())//I send the correct ID
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))//I send the correct data
                .with(user("admin").roles("ADMIN"))

        ).andExpect(status().isOk());//I verify that the code is 200
    }

    @Test
    @DisplayName("Attempt to delete a Category, unauthorized user: Error (Code 401 Unauthorized)")
    void deleteCategory__Unauthorized() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Created Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryService.delete(any(Category.class))).thenReturn(null);

        //I realize a request to the server with role Admin
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/"+ c1.getId())//I send the correct ID
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))
                //I don't send any role
        ).andExpect(status().isUnauthorized());// I verify that the code is 401
    }

    @Test
    @DisplayName("Attempt to delete a Category, unauthorized user: Error (Code 403 Forbidden)")
    void deleteCategory__isForbidden() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Created Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryService.delete(any(Category.class))).thenReturn(null);

        //I realize a request to the server with role User
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/"+c1.getId())//I send the correct ID
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))//I Send the data
                .with(user("user").roles("USER"))//I send the role User

        ).andExpect(status().isForbidden());//I verify that the code is 403
    }

    @Test
    @DisplayName("Attempt to delete a Category, wrong ID: Error (Code 404 Not Found)")
    void deleteCategory__DeleteIncorrectEntity_EntityNotFound() throws Exception {
        //I create the category that I will use
        Category c1 = new Category("1", "Created Category", "Description", null, null, false);

        //I set the correct functionality for the repository and service methods
        Mockito.when(categoryService.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryRepository.existsById( c1.getId() )).thenReturn(true);
        Mockito.when(categoryService.delete(any(Category.class))).thenReturn(null);

        //I realize a request to the server with role admin
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/"+2)//I send an incorrect ID
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1))
                .with(user("admin").roles("ADMIN"))

        ).andExpect(status().isNotFound());//I verify that the code is 404
    }
    //Delete Category Endpoint tests

    //Pagination endpoint tests
    @Test
    @DisplayName("Getting Categories Pagination with admin user: Success (Code 200 Ok)")
    void getAllPage__MethodIsOkWithAdmin() throws Exception {
            //I create the entity Response
            Map<String, Object> response = new LinkedHashMap<>();

            //I set the correct functionality for the service method
            when(categoryService.getAllPages(0)).thenReturn(response);

            //I realize a request to the server with role admin
            mockMvc.perform(MockMvcRequestBuilders.get("/categories/pages?page=")
                            .with(user("admin").roles("ADMIN"))
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(response)))

                    .andExpect(MockMvcResultMatchers.status().isOk());//I verify that the code is 200
    }

    @Test
    @DisplayName("Attempt to get the Categories Pagination: Error (Code 401 Unauthorized)")
    void getAllPage__UserIsUnauthorized() throws Exception {
        //I create the entity Response
        Map<String, Object> response = new LinkedHashMap<>();

        //I set the correct functionality for the service method
        when(categoryService.getAllPages(0)).thenReturn(response);

        //I realize a request to the server with role user
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/pages?page=")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(response)))
                //I don't send any role
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());//I verify that the code is 401
    }

    @Test
    @DisplayName("Attempt to get the Categories Pagination: Error (Code 400 Client Error)")
    void getAllPage__ClientError() throws Exception {
        //I throw an exception when I use the service
        when(categoryService.getAllPages(0)).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        //I realize a request that will fail due to the previous exception
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/pages?page=")
                        .contentType(APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN"))
                )

                .andExpect(MockMvcResultMatchers.status().is5xxServerError());//I verify that the error is any 5xx
    }
    //Pagination endpoint tests
}
