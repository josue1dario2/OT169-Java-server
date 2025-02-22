package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.entity.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;
import com.alkemy.ong.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final EmailService emailService;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, EmailService emailService) {
        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    @Override
    public ContactDto create(ContactDto contactDto) throws Exception {
        if(contactDto.getName().isEmpty() || contactDto.getName() == null){
            throw new Exception("Name is empty");
        }
        if(contactDto.getEmail().isEmpty() || contactDto.getEmail() == null){
            throw new Exception("Email is empty");
        }
        Contact newContact = Mapper.mapFromDto(contactDto,new Contact());
        contactRepository.save(newContact);
        emailService.sendContactMail(contactDto.getEmail(), contactDto.getName());
        return Mapper.mapToDto(newContact,contactDto);
    }


    @Override
    public List<ContactDto> getContactList() throws Exception {
            List<Contact> contacts = contactRepository.findAll();
            return  contacts.stream()
                    .map(entity -> Mapper.mapToDto(entity,new ContactDto()))
                    .collect(Collectors.toList());

    }
}
