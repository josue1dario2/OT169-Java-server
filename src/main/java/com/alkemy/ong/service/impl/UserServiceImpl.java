package com.alkemy.ong.service.impl;

import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.utils.JwtUtils;
import com.amazonaws.services.pinpoint.model.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User findByEmail(String email) throws Exception {

        if (email.isEmpty() || email == null){
            throw new IllegalAccessException("Mail is empty");
        }
        User usuario = userRepository.findByEmail(email);
        if (usuario==null){
            throw new NoSuchElementException("User not found");
        }
        return usuario;
    }

    @Override
    public ResponseEntity<?> updatePartialInfo(String id, Map<Object, Object> fields) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            return ResponseEntity.notFound().build();

        }
        isUserAllowed(id);
            try {
                fields.forEach((key, value) -> {
                    Field field = ReflectionUtils.findField(User.class, (String) key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, optionalUser.get(), value);
                });
                User updatedUser = userRepository.save(optionalUser.get());

                return new ResponseEntity<User>(updatedUser, HttpStatus.OK);

            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
    }

    @Override
    public void delete(String id) {

        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            isUserAllowed(id);
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getAllUser() {
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<String> emailList = userList.stream()
                .map(User::getEmail)
                .collect(Collectors.toList());

        return ResponseEntity.ok(emailList);
    }

    @Override
    public void isUserAllowed(String id) throws ForbiddenException {
        String jwt = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization"), email;
        User user;

        jwt = jwt.substring(7);
        email = jwtUtils.extractEmail(jwt);
        user = userRepository.findByEmail(email);

        if (!user.getId().equals(id) && !user.getRoleId().getName().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
