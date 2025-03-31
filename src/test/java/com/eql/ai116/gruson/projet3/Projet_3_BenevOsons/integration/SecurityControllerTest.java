package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.integration;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest.SecurityRestController;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class SecurityRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private SecurityRestController securityRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(securityRestController).build();
    }

    @Test
    void registerUser_Success() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setName("testUser");
        registrationDto.setPassword("testPassword");

        // Mock the behavior of the service
        when(securityService.register(any(RegistrationDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post("/api/rest/security/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\", \"password\":\"testPassword\"}"))
                .andExpect(status().isCreated());

        verify(securityService, times(1)).register(any(RegistrationDto.class));
    }

    @Test
    void registerUser_Failure() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setName("testUser");
        registrationDto.setPassword("testPassword");

        // Mock the behavior of the service to simulate a failure (e.g., bad request)
        when(securityService.register(any(RegistrationDto.class)))
                .thenReturn(new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/api/rest/security/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\", \"password\":\"testPassword\"}"))
                .andExpect(status().isBadRequest());

        verify(securityService, times(1)).register(any(RegistrationDto.class));
    }

    @Test
    void authenticate_Success() throws Exception {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setEmail("testUser");
        authenticationDto.setPassword("testPassword");

        // Mock the behavior of the service
        when(securityService.authenticate(any(AuthenticationDto.class)))
                .thenReturn(new ResponseEntity<>("Authenticated", HttpStatus.OK));

        mockMvc.perform(post("/api/rest/security/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\", \"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Authenticated"));

        verify(securityService, times(1)).authenticate(any(AuthenticationDto.class));
    }

    @Test
    void authenticate_Failure() throws Exception {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setEmail("testUser");
        authenticationDto.setPassword("testPassword");

        // Mock the behavior of the service to simulate a failure (e.g., unauthorized)
        when(securityService.authenticate(any(AuthenticationDto.class)))
                .thenReturn(new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED));

        mockMvc.perform(post("/api/rest/security/authenticate")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\", \"password\":\"testPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Unauthorized"));

        verify(securityService, times(1)).authenticate(any(AuthenticationDto.class));
    }
}
