package com.alkemy.ong.utils;

import com.alkemy.ong.dto.*;
import com.alkemy.ong.entity.*;
import com.alkemy.ong.repository.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class Mapper {

	public static OrganizationResponseDto mapToDto(Organization organization, OrganizationResponseDto dto) {
		dto.setName(organization.getName());
		dto.setImage(organization.getImage());
		dto.setPhone(organization.getPhone());
		dto.setAddress(organization.getAddress());

		Map<String, String> contactUrls = new HashMap<String, String>();
		contactUrls.put("Email", organization.getEmail());
		contactUrls.put("Facebook", organization.getFacebookUrl());
		contactUrls.put("LinkedIn", organization.getLinkedinUrl());
		contactUrls.put("Instagram", organization.getInstagramUrl());

		dto.setContact(contactUrls);

		return dto;
	}

	public static Organization mapFromDto(OrganizationRequestDto dto, Organization organization) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		modelMapper.map(dto, organization);
		return organization;

	}


	public static Category mapToEntity(CategoryBasicDto categoryDto, Category category) {
			category.setName(categoryDto.getName());
		return category;
	}

	public static CategoryBasicDto mapToDto(Category category, CategoryBasicDto basicDto) {
		basicDto.setName(category.getName());

		return basicDto;
	}

	public static ActivityDto mapToDto(Activity activity, ActivityDto dto) {
		dto.setId(activity.getId());
		dto.setName(activity.getName());
		dto.setContent(activity.getContent());
		dto.setImage(activity.getImage());
		return dto;
	}

	public static Activity mapFromDto(ActivityDto dto, Activity activity) {
		activity.setName(dto.getName());
		activity.setContent(dto.getContent());
		activity.setImage(dto.getImage());
		return activity;
	}

	public static UserDto mapToUserDto(User user) {

		return new UserDto(
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPhoto());
	}

	public static SlideDto mapToDto(Slide slide, SlideDto slideDto){
		slideDto.setImageUrl(slide.getImageUrl());
		slideDto.setOrder(slide.getOrder());
		return slideDto;
	}

	public SlideResponseDto fullSlideToDto(Slide slide){

			SlideResponseDto dto = new SlideResponseDto();
			OrganizationResponseDto orgDto = new OrganizationResponseDto();
			String idOrg = String.valueOf(slide.getOrganizationId());
			dto.setImgUrl(slide.getImageUrl());
			dto.setOrder(slide.getOrder());
			dto.setText(slide.getText());
			dto.setOrg(slide.getOrganizationId().getName());

			return dto;

	}

	public static CommentResponseDto mapToDto(Comment comment, CommentResponseDto dto) {
		dto.setBody(comment.getBody());
		return dto;
	}

	public Comment mapFromDto(CommentRequestDto commentRequestDto, Comment comment,User user, News news){
		comment.setBody(commentRequestDto.getBody());
		comment.setNewsId(news);
		comment.setUser_id(user);
		return comment;
	}

	public static ContactDto mapToDto(Contact contact, ContactDto contactDto){
		contactDto.setId(contact.getId());
		contactDto.setName(contact.getName());
		contactDto.setPhone(contact.getPhone());
		contactDto.setEmail(contact.getEmail());
		contactDto.setMessage(contact.getMessage());
		return contactDto;
	}
	public static Contact mapFromDto(ContactDto contactDto, Contact contact){
		contact.setId(contactDto.getId());
		contact.setName(contactDto.getName());
		contact.setPhone(contactDto.getPhone());
		contact.setEmail(contactDto.getEmail());
		contact.setMessage(contactDto.getMessage());
		return contact;
	}


	public static TestimonialDto mapToDto(Testimonial testimonial, TestimonialDto testimonialDto){
		testimonialDto.setId(testimonial.getId());
		testimonialDto.setName(testimonial.getName());
		testimonialDto.setImage(testimonial.getImage());
		testimonialDto.setContent(testimonial.getContent());

		return testimonialDto;
	}

	public static Testimonial mapFromDto(TestimonialDto testimonialDto, Testimonial testimonial){
		testimonial.setName(testimonialDto.getName());
		testimonial.setImage(testimonialDto.getImage());
		testimonial.setContent(testimonialDto.getContent());

		return testimonial;
	}

	public Testimonial updateValues(Testimonial testimonial, TestimonialDto testimonialDto){
		testimonial.setName(testimonialDto.getName());
		testimonial.setImage(testimonialDto.getImage());
		testimonial.setContent(testimonialDto.getContent());
		return testimonial;
	}

	public static MemberDto mapToDto(Member member, MemberDto dto) {
		dto.setId(member.getId());
		dto.setName(member.getName());
		dto.setDescription(member.getDescription());
		return dto;
	}

}
