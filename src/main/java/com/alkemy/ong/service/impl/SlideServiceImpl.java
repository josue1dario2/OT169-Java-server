package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import com.alkemy.ong.utils.SlideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private SlideMapper slideMapper;

    @Override
    public SlideResponseDto getSlideDetails(String id) throws Exception {

        Optional<Slide> find = slideRepository.findById(id);
        if (!find.isPresent()){
            throw new Exception("Slide not found");
        }
        Slide entity =find.get();
        SlideResponseDto dto = slideMapper.fullSlideToDto(entity);

        return dto;
    }
}
