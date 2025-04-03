package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Organization;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;
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
import java.util.List;
import java.util.Optional;


@Service
public class SecurityServiceImpl implements SecurityService {

    Logger logger = LogManager.getLogger();

    /// Attributs
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private VolonteerRepository volonteerRepository;
    private OrganizationRepository organizationRepository;
    private AdressServiceImpl adressServiceImpl;
    private AdressRepository adressRepository;

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

            List<Adress> adresses = registrationDto.getAdressList();

            for (Adress adress : adresses){
                Adress adressWithLatLon = adressServiceImpl.adressWithLatLon(adress);
                Optional<Adress> adressIsAlreadyExisting = adressRepository.findByLatitudeAndLongitude(
                        adressWithLatLon.getLatitude(),adressWithLatLon.getLongitude());
                if( adressIsAlreadyExisting.isEmpty()){
                    adressRepository.save(adressWithLatLon);
                } else adress.setAdress_id(adressIsAlreadyExisting.get().getAdress_id());
            }


            Role role = roleRepository.findByRoleName(registrationDto.getRoleName());

            if (registrationDto.getRoleName().equals(RoleName.VOLUNTEER)) {
                Volunteer volunteer = new Volunteer();
                volunteer.setEmail(registrationDto.getEmail());
                volunteer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
                volunteer.setPhoneNumber(registrationDto.getPhoneNumber());
                volunteer.setName(registrationDto.getName());
                volunteer.setUserAdressList(adresses);
                volunteer.setRegistrationDate(LocalDate.now());
                volunteer.setRole(role);
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
                organization.setRole(role);
                organization.setrna(registrationDto.getRna());

                logger.info("Enregistrement de l'organisation : " + organization);
                organizationRepository.save(organization);
            }

            String token = jwtUtilities.generateToken(registrationDto.getName(), role.getRoleName());
            logger.info("Inscription réussie pour : " + registrationDto.getEmail());

            User user = userRepository.findByEmail(registrationDto.getEmail());
            UserDto userDto = new UserDto(user.getUser_id(), user.getName(), token, role.getRoleName());

            return ResponseEntity.status(HttpStatus.OK).body(userDto);

        } catch (Exception e) {
            logger.error("Erreur lors de l'inscription : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue.");
        }
    }

    @Override
    public ResponseEntity<Object> authenticate(AuthenticationDto authenticationDto) {
        logger.info("Début de l'authentification pour : " + authenticationDto.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationDto.getEmail(),
                            authenticationDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByEmail(authentication.getName());

            logger.info(user);

            Role role =user.getRole();
            String roleName = role.getRoleName();

            String token = jwtUtilities.generateToken(user.getName(),roleName);

            UserDto userDto = new UserDto(user.getUser_id(), user.getName(), token, roleName);

            logger.info("Authentification réussie pour " + authenticationDto.getEmail());
            return ResponseEntity.ok(userDto);

        } catch (Exception e){

            logger.error("Erreur d'authentification : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur d'authentification. Veuillez réessayer.");
        }
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

    @Autowired
    public void setAdressRepository(AdressRepository adressRepository) {
        this.adressRepository = adressRepository;
    }

    @Autowired
    public void setAdressService(AdressServiceImpl adressServiceImpl) {
        this.adressServiceImpl = adressServiceImpl;
    }
}
