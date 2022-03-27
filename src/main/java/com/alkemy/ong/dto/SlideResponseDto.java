package com.alkemy.ong.dto;

import lombok.Data;

@Data
public class SlideResponseDto {

    private String imgUrl;

    private String text;

    private Integer order;

    private OrganizationDto organization;
}
/*
@Column(name= "Image_url")
    private String imageUrl;

    private String text;

    @Column(name= "slide_order")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slide_organizationId")
    private OrganizationEntity organizationId;
 */