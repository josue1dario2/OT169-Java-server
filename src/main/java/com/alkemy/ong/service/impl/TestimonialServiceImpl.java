package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import com.alkemy.ong.utils.Mapper;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import java.util.Optional;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public TestimonialDto save(TestimonialDto testimonialDto) throws Exception {
        try{
            Testimonial testimonial = mapper.mapFromDto(testimonialDto, new Testimonial());
            return mapper.mapToDto(testimonialRepository.save(testimonial), new TestimonialDto());
        }catch (Exception e){
            throw new Exception("Failed to create");
        }

    }

    @Override
    public void delete(String id) {
        Optional<Testimonial> optional = testimonialRepository.findById(id);

        if (optional.isPresent()) {
            testimonialRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public TestimonialDto updateTestimonials(TestimonialDto testimonialDto, String id) {
        Optional<Testimonial> optional = testimonialRepository.findById(id);

        if (optional.isPresent()) {
            Testimonial updatedTestimonials = mapper.updateValues(testimonialRepository.findById(id).get(), testimonialDto);
            testimonialRepository.save(updatedTestimonials);
                return testimonialDto;
        } else{
            throw new ParameterNotFoundException("");
        }
    }
    public Map<String, Object> getAllPages(Integer page) throws Exception {
        Integer size = 10;
        try {
            Pageable paging = PageRequest.of(page, size);
            String url = "/testimonials/pages?page=";

            Page<List<LinkedHashMap>> pagedTestimonials = testimonialRepository.findPage(paging);
            List<List<LinkedHashMap>> testimonialList = pagedTestimonials.getContent();
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("Total Items", pagedTestimonials.getTotalElements());
            response.put("Total Pages", pagedTestimonials.getTotalPages());
            response.put("Current Page", pagedTestimonials.getNumber());

            addNextPageToResponse(response, url, pagedTestimonials);
            addPreviousPageToResponse(response, url, pagedTestimonials);

            response.put("Testimonials", testimonialList);

            return response;
        } catch (Exception e) {
            throw new Exception("Failed to load pages", e);
        }
    }

    private void addNextPageToResponse(Map<String, Object> response, String url, Page<List<LinkedHashMap>> pagedTestimonials) {
        if (pagedTestimonials.getNumber() == pagedTestimonials.getTotalPages() - 1) {
            response.put("Next Page", "This is the last page");
        } else {
            response.put("Next Page", url.concat(String.valueOf(pagedTestimonials.getNumber() + 1)));
        }
    }

    private void addPreviousPageToResponse(Map<String, Object> response, String url, Page<List<LinkedHashMap>> pagedTestimonials) {
        if (pagedTestimonials.getNumber() == 0) {
            response.put("Previous Page", "This is the first page");
        } else {
            response.put("Previous Page", url.concat(String.valueOf(pagedTestimonials.getNumber() - 1)));
        }
    }


}
