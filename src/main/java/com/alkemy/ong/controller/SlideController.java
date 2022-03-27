package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/Slide")
public class SlideController {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private SlideService slideService;

    

    @GetMapping("/{id}")
    public ResponseEntity<SlideResponseDto> detailsSlide(@PathVariable String id) {
        SlideResponseDto dto = new SlideResponseDto();
        try {
            dto = slideService.getSlideDetails(id);

        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(dto);
    }
}