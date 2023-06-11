package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MembersResponseDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.entity.Member;
import com.alkemy.ong.service.MemberService;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@Api(tags = "Member Controller", value = "MemberEndpoints")
public class MemberController {
	private final MemberService memberService;
	@Autowired
	public MemberController(MemberService memberService){
		this.memberService = memberService;
	}


	@PostMapping(produces = {"application/json"}, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a member and return it.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "CREATED - The member was successfully created",
					response = Member.class),
			@ApiResponse(code = 400, message = "INVALID_ARGUMENT - Certain arguments "
					+ "cannot be empty or null."),
			@ApiResponse(code = 403, message = "PERMISSION_DENIED - Forbidden.")})
	public ResponseEntity<Member> createMember(@RequestBody Member member) { // I get the entity
		return new ResponseEntity<Member>(memberService.save(member), HttpStatus.OK); // if it doesn't had errors,
	}

	@DeleteMapping(value = "/{id}", produces = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete a member passed by id.")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "NO_CONTENT - The member was successfully deleted"),
			@ApiResponse(code = 403, message = "PERMISSION_DENIED - Forbidden."),
			@ApiResponse(code = 404, message = "NOT_FOUND - Member not found.")})
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id",
					value = "Id of the member we want to delete",
					required = true, allowEmptyValue = false,
					paramType = "path", dataTypeClass = String.class,
					example = "1")})
	public ResponseEntity<Member> deleteMember(@PathVariable String id){
		if(memberService.existsById(id)) {
			memberService.delete( memberService.getById(id) );//I delete the member
			return ResponseEntity.ok().build();
		}else{
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Updates a member")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Returns the entire updated member"),
			@ApiResponse(code = 401, message = "There aren't authorization headers"),
			@ApiResponse(code = 403, message = "Error, the user doesn't have the permissions to use this method"),
			@ApiResponse(code = 404, message = "Error, not found any Member with that ID")
	})
	@ApiImplicitParams(value = {@ApiImplicitParam(name = "id",
			value = "Id of the member we want to update",
			required = true, allowEmptyValue = false,
			paramType = "path", dataTypeClass = String.class,
			example = "1")})
	@PutMapping(value = "/{id}", produces = {"application/json"}, consumes = {"application/json"})
	public ResponseEntity<Member> updateMember(@PathVariable(name="id") String id,@RequestBody Member member){

		if(memberService.existsById(id)) {//If the member exists
			return new ResponseEntity<Member>(memberService.save(member), HttpStatus.OK);//I create the member
		}
		return ResponseEntity.status(NOT_FOUND).build();//If it doesn't or the Member is null/not valid, I throw 500 error code

	}

	@GetMapping(produces = {"application/json"})
	@ApiOperation(value = "Return member list per page.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK - The list of members. The size of the page is the one"
					+ "passed in the parameters", response = MembersResponseDto.class, responseHeaders = {
					@ResponseHeader(name = "Link",
							description = "Link of the previous page and another for the next page",
							response = String.class)}),
			@ApiResponse(code = 403, message = "PERMISSION_DENIED - Forbidden.")})//VER ROL
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "page", value = "Page of the list",
					required = true,
					paramType = "query",
					dataTypeClass = String.class,
					example = "0")})
	public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") Integer page){
		try{
			return ResponseEntity.status(OK).body(memberService.getAllMembers(page));
		}catch (Exception e){
			return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
		}
	}
}
