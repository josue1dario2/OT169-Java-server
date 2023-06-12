package com.alkemy.ong.dto;

import java.sql.Timestamp;
import java.time.Instant;

import lombok.Data;

@Data
public class CategoryDTO {

	private String id;
    private String name;
    private String description;
    private String image;
    private Timestamp timestamp = Timestamp.from(Instant.now());
    private boolean soft_delete = Boolean.FALSE;

    public CategoryDTO(String id, String description, String image, String name) {
    }
}
