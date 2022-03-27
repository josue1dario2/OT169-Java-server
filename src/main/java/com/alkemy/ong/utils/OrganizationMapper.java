package com.alkemy.ong.utils;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.entity.OrganizationEntity;

public class OrganizationMapper {

    public static OrganizationDto organizationToDto(OrganizationEntity org){
        OrganizationDto dto = new OrganizationDto();
        dto.setImage(org.getImage());
        dto.setPhone(org.getPhone());
        dto.setAddress(org.getAddress());

        return dto;
    }

}

