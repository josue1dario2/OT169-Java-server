package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationRequestDto;
import com.alkemy.ong.dto.OrganizationResponseDto;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.service.IOrganizationService;
import com.alkemy.ong.utils.Mapper;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

	@Autowired
	private OrganizationRepository repository;

	@Override
	public OrganizationResponseDto getPublicInfo() {
		return Mapper.mapToDto(repository.findAll().get(0), new OrganizationResponseDto());
	}
	@Override
	public OrganizationResponseDto postPublicInfo(OrganizationRequestDto organizationRequestDto) {
		Organization organization;
		if (repository.count() == 0) {
			organization = new Organization();
		} else {
			organization = repository.findAll().get(0);
		}
		Mapper.mapFromDto(organizationRequestDto, organization);
		repository.save(organization);
		return Mapper.mapToDto(organization, new OrganizationResponseDto());
	}
	
}
