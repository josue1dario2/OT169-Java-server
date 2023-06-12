package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.service.impl.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/contacts")
public class ContactController {
    private final ContactServiceImpl contactService;
    @Autowired
    public ContactController(ContactServiceImpl contactService){
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactDto> save(@RequestBody @Valid ContactDto dto) throws Exception {
       return ResponseEntity.status(CREATED).body(contactService.create(dto));

    }

    @GetMapping
    public ResponseEntity<List<ContactDto>> getContactList(){
        try{
            return ResponseEntity.status(OK).body(contactService.getContactList());
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
