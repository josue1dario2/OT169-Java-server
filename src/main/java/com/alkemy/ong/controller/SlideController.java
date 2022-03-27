package com.alkemy.ong.controller;

import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/Slide")
public class SlideController {

    @Autowired
    private SlideRepository slideRepository;

    @PostMapping
    private void createSlide(@Valid @RequestBody SlideRequestDto slideRequestDto){








    }

    @GetMapping("/{id}")
    public ResponseEntity<Slide> detailsSlide(@PathVariable Long id) {

    }
}