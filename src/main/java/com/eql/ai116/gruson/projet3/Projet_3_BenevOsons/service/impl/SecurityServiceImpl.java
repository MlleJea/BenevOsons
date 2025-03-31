package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Organization;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AdressDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.BearerToken;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.AdressRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.OrganizationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.RoleRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.VolonteerRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security.JwtUtilities;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class SecurityServiceImpl implements SecurityService {

    /// Attributs
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private VolonteerRepository volonteerRepository;
    private OrganizationRepository organizationRepository;
    private AdressRepository adressRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtilities jwtUtilities;


    @Override
    public ResponseEntity<Object> register(RegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Identifiant déjà utilisé");
        }

        // Registration in the User table
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setName(registrationDto.getName());

        // RegistrationDate calcul
        user.setRegistrationDate(LocalDate.now());

        // Role treatment
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByRoleName(registrationDto.getRoleName());

        if (role == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rôle non valide");
        }
        roles.add(role);
        user.setRole(Collections.singletonList(role));
        User userSaved = userRepository.save(user);

        // Registration in the Adress table
        AdressDto adressDto = registrationDto.getAdressDto();
        Adress adress = new Adress();
        adress.setCity(adressDto.getCity());
        adress.setPostalCode(adressDto.getPostalCode());
        adress.setStreetType(adressDto.getStreetType());
        adress.setStreetNumber(adressDto.getStreetNumber());
        adress.setStreetName(adressDto.getStreetName());
        adress.setLatitude(adressDto.getLatitude());
        adress.setLongitude(adressDto.getLongitude());

        adressRepository.save(adress);

        // Registration in the Volonteer or Oganization table
        if (registrationDto.getRoleName().equals(RoleName.VOLUNTEER)) {
            Volunteer volunteer = new Volunteer();
            volunteer.setFirstName(registrationDto.getFirstName());
            volunteer.setUser_id(userSaved.getUser_id());
            volonteerRepository.save(volunteer);

        } else if (registrationDto.getRoleName().equals(RoleName.ORGANIZATION)) {
            Organization organization = new Organization();
            organization.setUser_id(userSaved.getUser_id());
            organizationRepository.save(organization);
        }
        String token = jwtUtilities.generateToken(registrationDto.getName(), Collections.singletonList(role.getRoleName()));
        return ResponseEntity.status(HttpStatus.OK).body(new BearerToken(token , "Bearer "));
    }

    @Override
    public ResponseEntity<Object> authenticate(AuthenticationDto authenticationDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.getEmail(),
                        authenticationDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(authentication.getName());

        List<String> rolesNames = new ArrayList<>();
        for (Role role : user.getRole()) {
            rolesNames.add(role.getRoleName().toString());
        }

        String token = jwtUtilities.generateToken(user.getName(), rolesNames);

        UserDto userDto = new UserDto(user.getUser_id(), user.getName(), token);
        return ResponseEntity.ok(userDto);
    }

    /// Setters
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAdressRepository(AdressRepository adressRepository) {
        this.adressRepository = adressRepository;
    }

    @Autowired
    public void setVolonteerRepository(VolonteerRepository volonteerRepository) {
        this.volonteerRepository = volonteerRepository;
    }

    @Autowired
    public void setOrganizationRepository(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtUtilities(JwtUtilities jwtUtilities) {
        this.jwtUtilities = jwtUtilities;
    }
}
