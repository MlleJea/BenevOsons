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
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.AuthenticationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.EmailAlreadyExistsException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.PasswordDontMatchException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.RegistrationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.AdressRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.OrganizationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.RoleRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.VolonteerRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security.JwtUtilities;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.AdressService;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SecurityService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private AdressService adressService;
    private AdressRepository adressRepository;

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtilities jwtUtilities;

    @Transactional
    @Override
    public UserDto register(RegistrationDto registrationDto) throws EmailAlreadyExistsException, RegistrationFailedException {
        logger.info("Début de l'inscription pour : " + registrationDto.getEmail());

        try {
            if (userRepository.existsByEmail(registrationDto.getEmail())) {
                logger.warn("Email déjà utilisé: " + registrationDto.getEmail());
                throw new EmailAlreadyExistsException("L'email est déjà utilisé: " + registrationDto.getEmail());
            }
            // Password treatment
            if(!registrationDto.getPassword().equals(registrationDto.getConfirmationPassword())) {
                throw new PasswordDontMatchException("Les mots de passe ne correspondent pas");
            }

            // Adress treatment
            List<Adress> adresses = registrationDto.getAdressList();
            processAddresses(adresses);

            // Role treatment
            Role role = roleRepository.findByRoleName(registrationDto.getRoleName());
            if (role == null) {
                throw new RegistrationFailedException("Rôle non trouvé: " + registrationDto.getRoleName(), null);
            }

            if (registrationDto.getRoleName().equals(RoleName.VOLUNTEER)) {
                saveVolunteer(registrationDto, adresses, role);
            } else if (registrationDto.getRoleName().equals(RoleName.ORGANIZATION)) {
                saveOrganization(registrationDto, adresses, role);
            } else {
                throw new RegistrationFailedException("Type de rôle non pris en charge: " + registrationDto.getRoleName(), null);
            }


            String token = jwtUtilities.generateToken(registrationDto.getEmail(), role.getRoleName());

            User user = userRepository.findByEmail(registrationDto.getEmail());
            UserDto userDto = new UserDto(user.getUserId(), user.getName(), user.getEmail(), token, role.getRoleName());

            logger.info("Inscription réussie pour : " + registrationDto.getEmail());
            return userDto;

        } catch (EmailAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erreur lors de l'inscription : ", e);
            throw new RegistrationFailedException("Échec de l'inscription pour: " + registrationDto.getEmail(), e);
        }
    }

    private void processAddresses(List<Adress> adresses) {
        for (Adress adress : adresses) {
            Adress adressWithLatLon = adressService.adressWithLatLon(adress);
            Optional<Adress> adressIsAlreadyExisting = adressRepository.findByLatitudeAndLongitude(
                    adressWithLatLon.getLatitude(), adressWithLatLon.getLongitude());
            if (adressIsAlreadyExisting.isEmpty()) {
                adressRepository.save(adressWithLatLon);
            } else {
                adress.setAdressId(adressIsAlreadyExisting.get().getAdressId());
            }
        }
    }

    private void saveVolunteer(RegistrationDto registrationDto, List<Adress> adresses, Role role) {
        Volunteer volunteer = new Volunteer();
        volunteer.setEmail(registrationDto.getEmail());
        volunteer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        volunteer.setPhoneNumber(registrationDto.getPhoneNumber());
        volunteer.setName(registrationDto.getName());
        volunteer.setUserAdressList(adresses);
        volunteer.setRegistrationDate(LocalDate.now());
        volunteer.setRole(role);
        volunteer.setBirthdate(registrationDto.getBirthDate());

        logger.info("Enregistrement du bénévole : " + volunteer);
        volonteerRepository.save(volunteer);
    }

    private void saveOrganization(RegistrationDto registrationDto, List<Adress> adresses, Role role) {
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

    @Override
    public UserDto authenticate(AuthenticationDto authenticationDto) throws AuthenticationFailedException {
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

            if (user == null) {
                throw new AuthenticationFailedException("Utilisateur non trouvé après authentification");
            }

            Role role = user.getRole();
            String roleName = role.getRoleName();

            String token = jwtUtilities.generateToken(user.getEmail(), roleName);

            UserDto userDto = new UserDto(user.getUserId(), user.getName(), user.getEmail(), token, roleName);

            logger.info("Authentification réussie pour " + authenticationDto.getEmail());
            return userDto;

        } catch (AuthenticationException e) {
            logger.error("Échec de l'authentification : Identifiants incorrects", e);
            throw new AuthenticationFailedException("Identifiants incorrects");
        }
    }

    /// Private methods



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
    public void setAdressService(AdressService adressService) {
        this.adressService = adressService;
    }
}