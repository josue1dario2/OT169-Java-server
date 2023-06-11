package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.dto.SlideUpdateDto;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/Slides")
public class SlideController {

    private final SlideService slideService;
    @Autowired
    public SlideController(SlideService slideService){
        this.slideService = slideService;
    }


    @GetMapping
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(OK).body(slideService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    private ResponseEntity<Void> createSlide(@Valid @RequestBody SlideRequestDto slideRequestDto){
        try {
            slideService.createSlide(slideRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlideResponseDto> detailsSlide(@PathVariable String id) {
        try {
            SlideResponseDto dto = slideService.getSlideDetails(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable String id){
        try{
            slideService.deleteSlide(id);
            return ResponseEntity.status(OK).build();
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSlide(@PathVariable String id, @RequestBody SlideUpdateDto dto){
        try{
            slideService.updateSlide(id, dto);
            return ResponseEntity.status(OK).build();
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
