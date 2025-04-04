package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.unity;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Organization;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.AuthenticationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.EmailAlreadyExistsException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.RegistrationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.AdressRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.OrganizationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.RoleRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.VolonteerRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security.JwtUtilities;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl.SecurityServiceImpl;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.AdressService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private VolonteerRepository volonteerRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private AdressService adressService;

    @Mock
    private AdressRepository adressRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtilities jwtUtilities;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private SecurityServiceImpl securityService;

    private RegistrationDto volunteerRegistrationDto;
    private RegistrationDto organizationRegistrationDto;
    private AuthenticationDto authenticationDto;
    private Role volunteerRole;
    private Role organizationRole;
    private Adress adress;
    private List<Adress> adressList;
    private User volunteerUser;
    private User organizationUser;

    @BeforeEach
    void setUp() {
        // Initialisation des données de test
        volunteerRole = new Role();
        volunteerRole.setRoleName(RoleName.VOLUNTEER);

        organizationRole = new Role();
        organizationRole.setRoleName(RoleName.ORGANIZATION);

        adress = new Adress();
        adress.setAdressId(1L);
        adress.setStreetName("123 Test Street");
        adress.setCity("Test City");
        adress.setPostalCode("12345");
        adress.setLatitude(45.0);
        adress.setLongitude(45.0);

        adressList = new ArrayList<>();
        adressList.add(adress);

        // Configuration des données pour l'inscription d'un bénévole
        volunteerRegistrationDto = new RegistrationDto();
        volunteerRegistrationDto.setEmail("benevole@test.com");
        volunteerRegistrationDto.setPassword("password");
        volunteerRegistrationDto.setName("Benevole Test");
        volunteerRegistrationDto.setFirstName("Prenom Test");
        volunteerRegistrationDto.setBirthDate(LocalDate.of(1990, 1, 1));
        volunteerRegistrationDto.setPhoneNumber("0123456789");
        volunteerRegistrationDto.setRoleName(RoleName.VOLUNTEER);
        volunteerRegistrationDto.setAdressList(adressList);

        // Configuration des données pour l'inscription d'une organisation
        organizationRegistrationDto = new RegistrationDto();
        organizationRegistrationDto.setEmail("association@test.com");
        organizationRegistrationDto.setPassword("password");
        organizationRegistrationDto.setName("Association Test");
        organizationRegistrationDto.setPhoneNumber("0123456789");
        organizationRegistrationDto.setRoleName(RoleName.ORGANIZATION);
        organizationRegistrationDto.setRna(123456789L);
        organizationRegistrationDto.setAdressList(adressList);

        // Configuration des données pour l'authentification
        authenticationDto = new AuthenticationDto();
        authenticationDto.setEmail("benevole@test.com");
        authenticationDto.setPassword("password");

        // Configuration des entités utilisateur
        volunteerUser = new Volunteer();
        volunteerUser.setUserId(1L);
        volunteerUser.setEmail("benevole@test.com");
        volunteerUser.setName("Benevole Test");
        volunteerUser.setRole(volunteerRole);

        organizationUser = new Organization();
        organizationUser.setUserId(2L);
        organizationUser.setEmail("association@test.com");
        organizationUser.setName("Association Test");
        organizationUser.setRole(organizationRole);
    }

    @Test
    void registerVolunteer_Success() throws EmailAlreadyExistsException, RegistrationFailedException {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(adressService.adressWithLatLon(any(Adress.class))).thenReturn(adress);
        when(adressRepository.findByLatitudeAndLongitude(anyDouble(), anyDouble())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName(RoleName.VOLUNTEER)).thenReturn(volunteerRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtUtilities.generateToken(anyString(), anyString())).thenReturn("jwt-token");
        when(userRepository.findByEmail("benevole@test.com")).thenReturn(volunteerUser);

        // Act
        UserDto result = securityService.register(volunteerRegistrationDto);

        // Assert
        assertNotNull(result);
        assertEquals(volunteerUser.getUserId(), result.getId());
        assertEquals(volunteerUser.getName(), result.getName());
        assertEquals("jwt-token", result.getToken());
        assertEquals(RoleName.VOLUNTEER.toString(), result.getRole());

        // Verify
        verify(volonteerRepository, times(1)).save(any(Volunteer.class));
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    void registerOrganization_Success() throws EmailAlreadyExistsException, RegistrationFailedException {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(adressService.adressWithLatLon(any(Adress.class))).thenReturn(adress);
        when(adressRepository.findByLatitudeAndLongitude(anyDouble(), anyDouble())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName(RoleName.ORGANIZATION)).thenReturn(organizationRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtUtilities.generateToken(anyString(), anyString())).thenReturn("jwt-token");
        when(userRepository.findByEmail("association@test.com")).thenReturn(organizationUser);

        // Act
        UserDto result = securityService.register(organizationRegistrationDto);

        // Assert
        assertNotNull(result);
        assertEquals(organizationUser.getUserId(), result.getId());
        assertEquals(organizationUser.getName(), result.getName());
        assertEquals("jwt-token", result.getToken());
        assertEquals(RoleName.ORGANIZATION.toString(), result.getRole());

        // Verify
        verify(organizationRepository, times(1)).save(any(Organization.class));
        verify(volonteerRepository, never()).save(any(Volunteer.class));
    }

    @Test
    void register_EmailAlreadyExists() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> {
            securityService.register(volunteerRegistrationDto);
        });

        // Verify
        verify(volonteerRepository, never()).save(any(Volunteer.class));
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    void register_RoleNotFound() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(adressService.adressWithLatLon(any(Adress.class))).thenReturn(adress);
        when(adressRepository.findByLatitudeAndLongitude(anyDouble(), anyDouble())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName(any(RoleName.class))).thenReturn(null);

        // Act & Assert
        assertThrows(RegistrationFailedException.class, () -> {
            securityService.register(volunteerRegistrationDto);
        });

        // Verify
        verify(volonteerRepository, never()).save(any(Volunteer.class));
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    void authenticate_Success() throws AuthenticationFailedException {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getName()).thenReturn("benevole@test.com");
        when(userRepository.findByEmail("benevole@test.com")).thenReturn(volunteerUser);
        when(jwtUtilities.generateToken(anyString(), anyString())).thenReturn("jwt-token");

        // Act
        UserDto result = securityService.authenticate(authenticationDto);

        // Assert
        assertNotNull(result);
        assertEquals(volunteerUser.getUserId(), result.getId());
        assertEquals(volunteerUser.getName(), result.getName());
        assertEquals("jwt-token", result.getToken());
        assertEquals(RoleName.VOLUNTEER.toString(), result.getRole());
    }

    @Test
    void authenticate_BadCredentials() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        assertThrows(AuthenticationFailedException.class, () -> {
            securityService.authenticate(authenticationDto);
        });
    }

    @Test
    void authenticate_UserNotFound() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getName()).thenReturn("benevole@test.com");
        when(userRepository.findByEmail("benevole@test.com")).thenReturn(null);

        // Act & Assert
        assertThrows(AuthenticationFailedException.class, () -> {
            securityService.authenticate(authenticationDto);
        });
    }
}