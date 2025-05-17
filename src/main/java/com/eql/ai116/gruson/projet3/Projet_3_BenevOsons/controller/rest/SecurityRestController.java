/**
 * SecurityRestController.java
 * Contrôleur REST qui gère les opérations de sécurité de l'application.
 *
 * Ce contrôleur expose des endpoints permettant de:
 *  Enregistrer un nouvel utilisateur
 *  Authentifier un utilisateur existant
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */

package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.AuthenticationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.EmailAlreadyExistsException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.RegistrationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SecurityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/rest/security")
public class SecurityRestController {

    private static final Logger logger = LogManager.getLogger();

    /// Attributs
    private SecurityService securityService;

    /// EndPoints

    /**
     * Enregistre un nouvel utilisateur dans le système.
     *
     * @param registrationDto Objet contenant les informations d'enregistrement
     * @return ResponseEntity contenant les informations de l'utilisateur créé ou un message d'erreur
     */
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegistrationDto registrationDto) {
        logger.info("Demande d'inscription reçue pour l'email: " + registrationDto.getEmail());

        try {
            UserDto userDto = securityService.register(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (EmailAlreadyExistsException e) {
            logger.warn("Échec de l'inscription - Email déjà utilisé: " + registrationDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Identifiant déjà utilisé");
        } catch (RegistrationFailedException e) {
            logger.error("Échec de l'inscription - Erreur interne: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de l'inscription.");
        }
    }

    /**
     * Authentifie un utilisateur avec ses identifiants.
     *
     * @param authenticationDto Objet contenant les informations d'authentification
     * @return ResponseEntity contenant les informations de l'utilisateur authentifié ou un message d'erreur
     */
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationDto authenticationDto) {
        logger.info("Demande d'authentification reçue pour l'email: " + authenticationDto.getEmail());

        try {
            UserDto userDto = securityService.authenticate(authenticationDto);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (AuthenticationFailedException e) {
            if (e.getCause() instanceof BadCredentialsException) {
                logger.warn("Échec de l'authentification - Identifiants incorrects pour: " + authenticationDto.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
            } else {
                logger.error("Échec de l'authentification - Erreur interne: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de l'authentification.");
            }
        }
    }

    /// Setters
    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}