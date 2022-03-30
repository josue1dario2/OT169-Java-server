package com.alkemy.ong.dto;

import lombok.Data;

@Data
public class SlideResponseDto {

    private String imgUrl;

    private String text;

    private Integer order;

    private OrganizationResponseDto organization;
}
