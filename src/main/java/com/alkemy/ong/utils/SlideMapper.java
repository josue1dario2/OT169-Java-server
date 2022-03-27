package com.alkemy.ong.utils;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.entity.OrganizationEntity;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SlideMapper {

    @Autowired
    private OrganizationRepository orgRepository;



    public  SlideResponseDto fullSlideToDto(Slide slide){

        SlideResponseDto dto = new SlideResponseDto();
        OrganizationDto organizationDto;

        String idOrg = String.valueOf(slide.getOrganizationId());
        /*Creation OrganizationDto*/
        OrganizationEntity organizationEntity = orgRepository.findById(idOrg).get();
        organizationDto = OrganizationMapper.organizationToDto(organizationEntity);

        dto.setImgUrl(slide.getImageUrl());
        dto.setOrder(slide.getOrder());
        dto.setText(slide.getText());
        dto.setOrganization(organizationDto);
        return dto;



    }
}
