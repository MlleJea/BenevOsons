package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.unity;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.*;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.*;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security.JwtUtilities;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl.SecurityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceImplTest {

    @InjectMocks
    private SecurityServiceImpl securityService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private VolonteerRepository volonteerRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtilities jwtUtilities;

    private RegistrationDto registrationDto;
    private AuthenticationDto authenticationDto;
    private Role role;
    private User user;

    private void mockCommonRegisterDependencies() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByRoleName(any())).thenReturn(role);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtUtilities.generateToken(anyString(), anyList())).thenReturn("fakeToken");
        when(userRepository.findByEmail(anyString())).thenReturn(user);
    }

    @BeforeEach
    void setUp() {
        role = new Role(RoleName.VOLUNTEER);
        registrationDto = new RegistrationDto("test@example.com", "password", LocalDate.now(), null,
                "0606060606", RoleName.VOLUNTEER, "Test", "FirstName", LocalDate.of(1990, 1, 1), new Adress());

        authenticationDto = new AuthenticationDto("test@example.com", "password");

        user = new User(1L, "TestUser", "encodedPassword", LocalDate.now(), "Test", List.of(role), new ArrayList<>(), "0606060606");
    }

    @Test
    void shouldRegisterVolunteerSuccessfully() {
        mockCommonRegisterDependencies();

        ResponseEntity<Object> response = securityService.register(registrationDto);

        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(UserDto.class, response.getBody());

        UserDto userDto = (UserDto) response.getBody();
        assertEquals("Test", userDto.getName());
        assertNotNull(userDto.getToken());
        assertEquals("VOLUNTEER", userDto.getRole());

        verify(volonteerRepository).save(any(Volunteer.class));
    }

    @Test
    void shouldReturnConflictWhenUserAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ResponseEntity<Object> response = securityService.register(registrationDto);

        assertEquals(409, response.getStatusCodeValue());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldAuthenticateSuccessfully() {
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(jwtUtilities.generateToken(anyString(), anyList())).thenReturn("fakeToken");

        ResponseEntity<Object> response = securityService.authenticate(authenticationDto);

        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(UserDto.class, response.getBody());

        UserDto userDto = (UserDto) response.getBody();
        assertEquals("Test", userDto.getName());
        assertNotNull(userDto.getToken());
    }

    @Test
    void shouldReturnBadRequestForInvalidRole() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByRoleName(any())).thenReturn(null);

        ResponseEntity<Object> response = securityService.register(registrationDto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Rôle non valide", response.getBody());
    }

    @Test
    void shouldReturnInternalServerErrorOnException() {
        when(userRepository.existsByEmail(anyString())).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<Object> response = securityService.register(registrationDto);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Une erreur est survenue.", response.getBody());
    }

    @Test
    void shouldReturnUnauthorizedForInvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        ResponseEntity<Object> response = securityService.authenticate(authenticationDto);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erreur d'authentification. Veuillez réessayer.", response.getBody());
    }

    @Test
    void shouldReturnInternalServerErrorWhenUserNotFound() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<Object> response = securityService.authenticate(authenticationDto);

        assertEquals(500, response.getStatusCodeValue());
    }

}
