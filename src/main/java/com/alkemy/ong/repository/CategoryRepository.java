package com.alkemy.ong.repository;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

	@Query("SELECT a.name FROM Category a")
	public List<Category> getNamesFromAll();

	@Query(value="SELECT id, name, description, image from Categories",nativeQuery=true)
	Page<List<LinkedHashMap>> findPage(Pageable pageable);
}
