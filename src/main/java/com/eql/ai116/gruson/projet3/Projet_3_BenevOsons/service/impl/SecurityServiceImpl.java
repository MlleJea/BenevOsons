package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Organization;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
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
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger logger = LogManager.getLogger();

    /// Attributs
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private VolonteerRepository volonteerRepository;
    private OrganizationRepository organizationRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtilities jwtUtilities;

    @Transactional
    @Override
    public ResponseEntity<Object> register(RegistrationDto registrationDto) {
        logger.info("Début de l'inscription pour : " + registrationDto.getEmail());

        try {
            if (userRepository.existsByEmail(registrationDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Identifiant déjà utilisé");
            }

            // Traitement de l'adresse
            Adress adress = registrationDto.getAdress();
            List<Adress> adresses = new ArrayList<>();
            adresses.add(adress);

            // Récupération du rôle
            Role role = roleRepository.findByRoleName(registrationDto.getRoleName());
            if (role == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rôle non valide");
            }
            List<Role> roles = new ArrayList<>();
            roles.add(role);

            // Création de l'utilisateur
            if (registrationDto.getRoleName().equals(RoleName.VOLUNTEER)) {
                Volunteer volunteer = new Volunteer();
                volunteer.setEmail(registrationDto.getEmail());
                volunteer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
                volunteer.setPhoneNumber(registrationDto.getPhoneNumber());
                volunteer.setName(registrationDto.getName());
                volunteer.setUserAdressList(adresses);
                volunteer.setRegistrationDate(LocalDate.now());
                volunteer.setRole(roles);
                volunteer.setFirstName(registrationDto.getFirstName());
                volunteer.setBirthdate(registrationDto.getBirthDate());

                logger.info("Enregistrement du bénévole : " + volunteer);
                volonteerRepository.save(volunteer);
            } else if (registrationDto.getRoleName().equals(RoleName.ORGANIZATION)) {
                Organization organization = new Organization();
                organization.setEmail(registrationDto.getEmail());
                organization.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
                organization.setPhoneNumber(registrationDto.getPhoneNumber());
                organization.setName(registrationDto.getName());
                organization.setUserAdressList(adresses);
                organization.setRegistrationDate(LocalDate.now());
                organization.setRole(roles);

                logger.info("Enregistrement de l'organisation : " + organization);
                organizationRepository.save(organization);
            }

            String token = jwtUtilities.generateToken(registrationDto.getName(), Collections.singletonList(role.getRoleName()));
            logger.info("Inscription réussie pour : " + registrationDto.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(new BearerToken(token, "Bearer "));
        } catch (Exception e) {
            logger.error("Erreur lors de l'inscription : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue.");
        }
    }

    @Override
    public ResponseEntity<Object> authenticate(AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationManager.authenticate(
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
