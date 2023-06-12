package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDto;

import java.util.Map;

public interface TestimonialService {

     TestimonialDto save(TestimonialDto testimonialDto) throws Exception;

    void delete(String id);

    TestimonialDto updateTestimonials(TestimonialDto testimonialDto, String id);

    Map<String, Object> getAllPages (Integer page) throws Exception;

}
