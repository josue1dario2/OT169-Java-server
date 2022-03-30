package com.alkemy.ong.utils;


import com.alkemy.ong.dto.OrganizationResponseDto;
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
        OrganizationResponseDto orgDto = new OrganizationResponseDto();
        String idOrg = String.valueOf(slide.getOrganizationId());
        /*Creation OrganizationDto*/
        OrganizationEntity organizationEntity = orgRepository.findById(idOrg).get();
        OrganizationResponseDto last= Mapper.mapToDto(organizationEntity, orgDto);
        dto.setImgUrl(slide.getImageUrl());
        dto.setOrder(slide.getOrder());
        dto.setText(slide.getText());
        dto.setOrganization(last);
        return dto;



    }
}
