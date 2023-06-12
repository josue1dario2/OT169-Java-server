package com.alkemy.ong.controller;

import com.alkemy.ong.entity.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.impl.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private MemberService service;

    @MockBean
    private MemberRepository repository;

    ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    Member miembroFull = new Member();





    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        String id = String.valueOf(UUID.randomUUID());
        miembroFull = new Member();
        miembroFull.setId(id);
        miembroFull.setName("NuevoValor");
        miembroFull.setFacebookUrl("facebook.com");
        miembroFull.setInstagramUrl("instagram.com");
        miembroFull.setLinkedinUrl("linkedin.com");
        miembroFull.setImage("img.jpg");
        miembroFull.setDescription("Prueba modificada");
        miembroFull.setTimestamp(Timestamp.from(Instant.now()));
        miembroFull.setSoftDelete(false);

    }
   //   ******** TEST CREATED MEMBERS ********
   //   **************************************
    /*Create Member*/
    /*Method return 200 Ok*/
    @Test
    @DisplayName("Create member, method should save new member and return 200 ok if the user has Role 'ADMIN'")
    void createMemberIfUserHasRoleAdmin()throws Exception{


        when(service.save(miembroFull)).thenReturn(miembroFull);

        mockMvc.perform(post("/members/")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(miembroFull))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                        .andExpect(status().isOk());
    }

    /*Method return 400 Bad Request*/
    @Test
    @DisplayName("Create member, method should return 400 Bad Request if request is not valid'")
    void createMemberReturnBadRequest()throws Exception{



        when(service.save(null)).thenReturn(null);

        mockMvc.perform(post("/members/")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }





    /*Update Members*/
    /*Method return 200 OK if user is ADMIN*/
    @Test
    @DisplayName("Update member, method should save update member and return 200 ok if the user has Role 'ADMIN'")
    void updateMemberIfUserHasRoleAdmin()throws Exception{
        String id = String.valueOf(UUID.randomUUID());
        String url = String.format("/members/%s", id);

        Member guardado = new Member(id,"Prueba","facebook.com","instagram.com","linkedin.com","img.jpg","Es una prueba",
                Timestamp.from(Instant.now()), false);



        when(service.existsById(id)).thenReturn(true);
        when(service.save(miembroFull)).thenReturn(miembroFull);

        mockMvc.perform(put(url)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(miembroFull))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());


    }

    /*Update Members*/
    /*Method return 400*/
    @Test
    @DisplayName("Update member, method should return 404 Not found if Id is not valid")
    void updateMemberIfUserHasRoleAdmidNotFoundIfIdNotValid()throws Exception{

        String id = "asdas";
        Member member = new Member();
        String url = String.format("/members/%s", id);

        when(service.existsById(id)).thenReturn(false);


        mockMvc.perform(put(url)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(member))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isNotFound());



    }
                                    /*Update Members*/
    /*Method return 400 if request is not valid*/
    @Test
    @DisplayName("Update member, method should return 400 Bad Request if request is not valid")
    void updateMemberIfUserHasRoleAdminBadRequest()throws Exception{

        String id = miembroFull.getId();
        Member member = null;
        String url = String.format("/members/%s", id);

        when(service.existsById(id)).thenReturn(false);
        when(service.save(member)).thenReturn(member);

        mockMvc.perform(put(url)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(member))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());



    }




                    /*Delete Members*/
    /*Return 200*/
    @Test
    @DisplayName("Delete member, method should delete member and return 200 ok if the user has Role 'ADMIN'")
    void deleteMemberIfUserHasRoleAdmin()throws Exception{

        String id = miembroFull.getId();
        String url= String.format("/members/%s", id);

        when(service.existsById(id)).thenReturn(true);
        when(service.delete(miembroFull)).thenReturn(ResponseEntity.ok(miembroFull));

        mockMvc.perform(delete(url)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(miembroFull))
                .with(user("user").roles("ADMIN"))
                .with(csrf()))
                .andExpect(status().isOk());


    }

    /*Return 500*/
    @Test
    @DisplayName("Delete member, method should return 500 if ID is not valid")
    void deleteMemberReturn500IfIdIsNotValid()throws Exception{

        String id = "prueba";
        String url= String.format("/members/%s", id);

        when(service.existsById(id)).thenReturn(false);


        mockMvc.perform(delete(url)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(miembroFull))
                        .with(user("user").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().is5xxServerError());


    }
    @Test
    @DisplayName("Delete member, method should return 401 if ID is not valid")
    void deleteMemberReturn401IfUserIsNotAuthenticate()throws Exception{

        String id = "prueba";
        String url= String.format("/members/%s", id);

        when(service.existsById(id)).thenReturn(true);


        mockMvc.perform(delete(url)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(miembroFull))
                        .with(csrf()))
                .andExpect(status().isUnauthorized());


    }




    /*Get All*/

    @Test
    @DisplayName("List members, method should List member and return 200 ok if the user has Role 'ADMIN'")
    void listMemberIfUserHasRoleAdmin()throws Exception{

        Integer page = 0;
        ResponseEntity r = new ResponseEntity<>(HttpStatus.OK);


        Object MembersResponseDto;
        when(service.getAllMembers(page)).thenReturn(r);
        mockMvc.perform(get("/members/?page=0")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(r))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("List members, method return 404 ")
    void listMemberReturnError()throws Exception {

        Integer page = null;
        String bad = "fse";
        ResponseEntity r = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        String url= String.format("/members/?page=", bad);

        Object MembersResponseDto;
        when(service.getAllMembers(page)).thenThrow();
        mockMvc.perform(get(url)
                .contentType(APPLICATION_JSON)

                .with(user("admin").roles("ADMIN"))
                .with(csrf()))
                .andExpect(status().isNotFound());
    }





    }