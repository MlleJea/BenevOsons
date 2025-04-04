package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.integration;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.RoleRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private RegistrationDto volunteerRegistrationDto;
    private RegistrationDto organizationRegistrationDto;
    private AuthenticationDto authenticationDto;

    @BeforeEach
    void setUp() {
        ensureRolesExist();

        Adress adress = new Adress();
        adress.setStreetName("123 Test Street");
        adress.setCity("Test City");
        adress.setPostalCode("12345");

        List<Adress> adressList = new ArrayList<>();
        adressList.add(adress);

        String uniqueEmail = "test_" + System.currentTimeMillis() + "@example.com";

        volunteerRegistrationDto = new RegistrationDto();
        volunteerRegistrationDto.setEmail(uniqueEmail);
        volunteerRegistrationDto.setPassword("Password123!");
        volunteerRegistrationDto.setName("Test Volunteer");
        volunteerRegistrationDto.setFirstName("First");
        volunteerRegistrationDto.setBirthDate(LocalDate.of(1990, 1, 1));
        volunteerRegistrationDto.setPhoneNumber("0123456789");
        volunteerRegistrationDto.setRoleName(RoleName.VOLUNTEER);
        volunteerRegistrationDto.setAdressList(adressList);

        String uniqueOrgEmail = "org_" + System.currentTimeMillis() + "@example.com";

        organizationRegistrationDto = new RegistrationDto();
        organizationRegistrationDto.setEmail(uniqueOrgEmail);
        organizationRegistrationDto.setPassword("Password123!");
        organizationRegistrationDto.setName("Test Organization");
        organizationRegistrationDto.setPhoneNumber("0123456789");
        organizationRegistrationDto.setRoleName(RoleName.ORGANIZATION);
        organizationRegistrationDto.setRna(123456789L);
        organizationRegistrationDto.setAdressList(adressList);

        authenticationDto = new AuthenticationDto();
        authenticationDto.setEmail(uniqueEmail);
        authenticationDto.setPassword("Password123!");
    }

    private void ensureRolesExist() {
        if (roleRepository.findByRoleName(RoleName.VOLUNTEER) == null) {
            Role volunteerRole = new Role();
            volunteerRole.setRoleName(RoleName.VOLUNTEER);
            roleRepository.save(volunteerRole);
        }

        if (roleRepository.findByRoleName(RoleName.ORGANIZATION) == null) {
            Role organizationRole = new Role();
            organizationRole.setRoleName(RoleName.ORGANIZATION);
            roleRepository.save(organizationRole);
        }
    }

    @Test
    void registerVolunteer_thenAuthenticate() throws Exception {

        MvcResult registerResult = mockMvc.perform(post("/api/rest/security/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(volunteerRegistrationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Volunteer"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.role").value("VOLUNTEER"))
                .andReturn();


        String responseJson = registerResult.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(responseJson, UserDto.class);

        User savedUser = userRepository.findByEmail(volunteerRegistrationDto.getEmail());
        assertNotNull(savedUser);
        assertEquals("Test Volunteer", savedUser.getName());
        assertEquals(RoleName.VOLUNTEER.toString(), savedUser.getRole().getRoleName());

        mockMvc.perform(post("/api/rest/security/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.name").value("Test Volunteer"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.role").value("VOLUNTEER"));
    }

    @Test
    void registerOrganization_thenAuthenticate() throws Exception {
        MvcResult registerResult = mockMvc.perform(post("/api/rest/security/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationRegistrationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Organization"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.role").value("ORGANIZATION"))
                .andReturn();

        String responseJson = registerResult.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(responseJson, UserDto.class);

        // Vérification en base de données
        User savedUser = userRepository.findByEmail(organizationRegistrationDto.getEmail());
        assertNotNull(savedUser);
        assertEquals("Test Organization", savedUser.getName());
        assertEquals(RoleName.ORGANIZATION.toString(), savedUser.getRole().getRoleName());

        AuthenticationDto orgAuthDto = new AuthenticationDto();
        orgAuthDto.setEmail(organizationRegistrationDto.getEmail());
        orgAuthDto.setPassword("Password123!");

        mockMvc.perform(post("/api/rest/security/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orgAuthDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.name").value("Test Organization"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.role").value("ORGANIZATION"));
    }

    @Test
    void registerWithExistingEmail_shouldReturnConflict() throws Exception {
        mockMvc.perform(post("/api/rest/security/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(volunteerRegistrationDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/rest/security/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(volunteerRegistrationDto)))
                .andExpect(status().isConflict())
                .andExpect(result -> assertEquals("Identifiant déjà utilisé", result.getResponse().getContentAsString()));
    }

    @Test
    void authenticateWithInvalidCredentials_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/api/rest/security/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(volunteerRegistrationDto)))
                .andExpect(status().isCreated());

        AuthenticationDto wrongAuthDto = new AuthenticationDto();
        wrongAuthDto.setEmail(volunteerRegistrationDto.getEmail());
        wrongAuthDto.setPassword("WrongPassword123!");

        mockMvc.perform(post("/api/rest/security/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongAuthDto)))
                .andExpect(status().isUnauthorized());
    }

}