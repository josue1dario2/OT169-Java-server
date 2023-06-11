package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationRequestDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.OrganizationResponseDto;
import com.alkemy.ong.service.impl.OrganizationServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/organization")
@Validated
@CrossOrigin
public class OrganizationController {
	
	@Autowired
	private OrganizationServiceImpl service;
	@Autowired
	private SlideService slideService;

	@GetMapping("/public") 
	public OrganizationResponseDto getPublicInfo() {
		return service.getPublicInfo();
	}

	@PostMapping("/public")
	public OrganizationResponseDto postPublicInfo(@RequestBody @Valid OrganizationRequestDto organizationRequestDto) {
		return service.postPublicInfo(organizationRequestDto);
	}

	@GetMapping("/public/Slides/{id}")
	public ResponseEntity<List<SlideResponseDto>> slideList(@PathVariable String id) throws Exception {
		return ResponseEntity.ok().body(slideService.slideForOng(id));
	}
}
