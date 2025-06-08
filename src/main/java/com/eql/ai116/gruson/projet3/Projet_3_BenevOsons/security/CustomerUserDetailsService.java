package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service personnalisé pour charger les détails des utilisateurs lors de l'authentification.
 *
 * Cette classe implémente l'interface UserDetailsService de Spring Security
 * pour fournir une logique personnalisée de chargement des utilisateurs
 * basée sur l'adresse email.
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    /**
     * Logger pour enregistrer les événements et erreurs de la classe.
     */
    Logger logger = LogManager.getLogger();

    /**
     * Repository pour accéder aux données des utilisateurs.
     * Injecté par son mutateur.
     */
    private UserRepository userRepository;

    /**
     * Charge un utilisateur par son nom d'utilisateur (email dans ce cas).
     *
     * Cette méthode est appelée par Spring Security lors du processus d'authentification.
     * Elle recherche l'utilisateur par son adresse email et retourne un objet
     * UserDetails personnalisé si l'utilisateur est trouvé.
     *
     * @param email L'adresse email de l'utilisateur à charger
     * @return Un objet CustomUserDetails contenant les informations de l'utilisateur
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé avec cette adresse email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.error(email);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new CustomUserDetails(user);
    }

    /**
     * Injecte le repository des utilisateurs.
     *
     * @param userRepository Le repository à injecter
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}