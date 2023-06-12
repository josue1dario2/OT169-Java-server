package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserCredentialsDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.Role;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.impl.EmailService;
import com.alkemy.ong.service.impl.UserDetailsCustomServiceImpl;
import com.alkemy.ong.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Optional;

import static com.alkemy.ong.controller.ActivitiesTest.asJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "")
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtUtils jwtUtil;

    @MockBean
    private UserDetailsCustomServiceImpl userDetailsCustomService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private EmailService emailService;

    @MockBean
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    private final String commonUrl = "/auth";

    private MockMvc mockMvc;

    private UserCredentialsDto userCredentialsDto;

    private User user;

    private com.alkemy.ong.entity.User userEntity;

    private Role roleEntity;

    private UserDto userDto;

    private String jwt;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail("eva@mail.com");
        userCredentialsDto.setPassword("1234");

        user = new User("eva@mail.com","1234", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        jwt = jwtUtil.generateToken(user);

        userDto = new UserDto();
        userDto.setFirstName("Maria Eva");
        userDto.setLastName("De los angeles");
        userDto.setPhoto("eva.jpg");
        userDto.setEmail("eva@mail.com");

        roleEntity = new Role();
        roleEntity.setId("2");
        roleEntity.setDescription("Role with administrator privileges");
        roleEntity.setName("USER");

        userEntity = new com.alkemy.ong.entity.User();
        userEntity.setId("10");
        userEntity.setEmail("eva@mail.com");
        userEntity.setPhoto("eva.jpg");
        userEntity.setFirstName("Maria Eva");
        userEntity.setLastName("De los angeles");
        userEntity.setPassword("1234");
        userEntity.setRoleId(roleEntity);
    }

    /*==================================
            TEST FOR USER LOGIN
     ===================================*/

    @Test
    @DisplayName("User login with correct credentials and should return a 200 an a authentication response with the jwt")
    void loginUser__ShouldLoginUserAndReturnOkAndJwt() throws Exception{

        usernamePasswordAuthenticationToken.setDetails(userCredentialsDto);

        when(userDetailsCustomService.loadUserByUsername(userCredentialsDto.getEmail())).thenReturn(user);
        when(usernamePasswordAuthenticationToken.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(any());

        mockMvc.perform(post(commonUrl +"/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(userCredentialsDto)))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.jwt").value(jwt)));
    }

    @Test
    @DisplayName("User login with incorrect credentials and should return a 400 bad request")
    void userLogin__shouldReturnBadRequestIfUserCredentialsAreIncorrect() throws Exception{

        userCredentialsDto.setEmail("");

        when(userDetailsCustomService.loadUserByUsername(userCredentialsDto.getEmail())).thenThrow(new BadCredentialsException("Incorrect email or password"));

        mockMvc.perform(post(commonUrl + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(userCredentialsDto)))
                .andExpect(status().isBadRequest());
    }

    /*================================================================
            TEST FOR THE AUTHENTICATED USER TO OBTAIN THEIR DATA
      ================================================================*/

    @Test
    @DisplayName("Get authenticated user data should return 200 ok and return user data")
    @WithMockUser(username = "eva@mail.com",password = "1234",roles = "ADMIN")
    void getAuthenticatedUserData__shouldReturnOkAndUserData() throws Exception{

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(userEntity);
        when(usernamePasswordAuthenticationToken.isAuthenticated()).thenReturn(true);

        mockMvc.perform(get(commonUrl + "/me").header(HttpHeaders.AUTHORIZATION,"Bearer "+jwt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Maria Eva"))
                .andExpect(jsonPath("$.lastName").value("De los angeles"))
                .andExpect(jsonPath("$.email").value("eva@mail.com"))
                .andExpect(jsonPath("$.photo").value("eva.jpg"));
    }

    @Test
    @DisplayName("Get authneticated user data should return 401 Unauthorized if a valid JWT is missing in the request or user is not logged in")
    void getAuthenticatedUserData__shouldReturn401UnauthorizedIfAValidJwtIsMissing() throws Exception{

        mockMvc.perform(get(commonUrl + "/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    /*=======================================
            TEST FOR REGISTER AN USER
      =======================================*/

    @Test
    @DisplayName("Register new user should return 200 ok and the jwt in the authentication response")
    void registerUser__shouldReturn200OkAndJwt() throws Exception{

        when(userRepository.save(userEntity)).thenReturn(null);
        when(roleRepository.findById(roleEntity.getId())).thenReturn(Optional.of(roleEntity));
        when(userDetailsCustomService.loadUserByUsername(userEntity.getEmail())).thenReturn(user);
        when(usernamePasswordAuthenticationToken.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(any());

        mockMvc.perform(post(commonUrl +"/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(userEntity)))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.jwt").value(jwt)));
    }

    @Test
    @DisplayName("Register new user should return 400 bad request if request is not valid")
    void registerUser__shouldReturn400BadRequestIfRequestIsNotValid() throws Exception{

        userEntity.setFirstName(null);

        when(userRepository.save(userEntity)).thenReturn(null);
        when(roleRepository.findById(roleEntity.getId())).thenReturn(Optional.of(roleEntity));

        mockMvc.perform(post(commonUrl +"/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(userEntity)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Register new user should return 409 conflict if the email is already taken")
    void registerUser__shouldReturn409ConflictIfTheEmailIsAlreadyTaken() throws Exception {

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(userEntity);

        mockMvc.perform(post(commonUrl + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(userEntity)))
                .andExpect(status().isConflict());
    }
}
