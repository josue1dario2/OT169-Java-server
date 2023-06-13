package com.alkemy.ong.controller;

import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService  userService;
    @Autowired
    public UserController(UserService  userService){
        this.userService = userService;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable  String id) {
       userService.delete(id);
       return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody Map<Object, Object> fields){
        return userService.updatePartialInfo(id,fields);
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        return userService.getAllUser();
    }


}
