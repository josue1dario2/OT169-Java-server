package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.MembersResponseDto;
import com.alkemy.ong.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.alkemy.ong.entity.Member;
import com.alkemy.ong.repository.MemberRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	@Autowired
	public MemberService(MemberRepository memberRepository){
		this.memberRepository = memberRepository;
	}
	
	//Create / Update
	public Member save(Member member) {
		return memberRepository.save(member);	
	}
	
	//Delete
	public ResponseEntity<Member> delete(Member member) {
		memberRepository.delete(member);	
		return new ResponseEntity<Member>(HttpStatus.OK);
	}
	//List all
	public ResponseEntity<?> getAllMembers(Integer page) throws Exception {

		if (page.equals(null) || page.equals(String.class)){
			throw new Exception("Unexpected value");
		}
		MembersResponseDto response = new MembersResponseDto();

		Page<Member> membersPage = memberRepository.findAll(PageRequest.of(page, 10));

		if(membersPage.getTotalPages() < 0 || membersPage.isEmpty()){
			throw new Exception("Members is empty");
		}

		List<MemberDto> members = membersPage.stream()
													 .map(member -> Mapper.mapToDto(member, new MemberDto()))
													 .collect(Collectors.toList());


		Map<String, String> pages = new LinkedHashMap<>();
		String url = "/members?page=";


		if(membersPage.isFirst()) {
			pages.put("previous", "This is the first page.");
			pages.put("next", url.concat(String.valueOf(membersPage.getNumber()+1)));
		} else if(membersPage.isLast()) {
			pages.put("previous", url.concat(String.valueOf(membersPage.getNumber()-1)));
			pages.put("next", "This is the last page.");
		} else {
			pages.put("previous", url.concat(String.valueOf(membersPage.getNumber()-1)));
			pages.put("next", url.concat(String.valueOf(membersPage.getNumber()+1)));
		}

		response.setPages(pages);
		response.setMembers(members);

		return ResponseEntity.ok().body(response);
	}

	//Exists
	public boolean existsById(String id) {
		return memberRepository.existsById(id);
	}

	public Member getById(String id){
		return memberRepository.getById(id);
	}
}
