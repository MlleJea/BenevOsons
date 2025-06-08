package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre d'authentification JWT pour intercepter et valider les tokens JWT.
 *
 * Ce filtre étend OncePerRequestFilter pour s'assurer qu'il ne s'exécute
 * qu'une seule fois par requête. Il vérifie la présence et la validité
 * des tokens JWT dans les en-têtes des requêtes HTTP.
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Logger pour enregistrer les événements du filtre.
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Utilitaire pour les opérations JWT.
     * Injecté par son mutateur.
     */
    private JwtUtilities jwtUtilities;

    /**
     * Service pour charger les détails des utilisateurs.
     * Injecté par son mutateur.
     */
    private CustomerUserDetailsService customerUserDetailsService;

    /**
     * Méthode principale du filtre qui traite chaque requête HTTP.
     *
     * Cette méthode :
     * 1. Exclut certaines routes du filtrage JWT (comme /register)
     * 2. Extrait et valide le token JWT de la requête
     * 3. Authentifie l'utilisateur si le token est valide
     * 4. Définit le contexte de sécurité pour la requête
     *
     * @param request La requête HTTP entrante
     * @param response La réponse HTTP sortante
     * @param filterChain La chaîne de filtres à continuer
     * @throws ServletException En cas d'erreur de servlet
     * @throws IOException En cas d'erreur d'entrée/sortie
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("Requête reçue");

        // Exclusion de la route d'enregistrement du filtrage JWT
        if (request.getRequestURI().contains("/api/rest/security/register")) {
            logger.info("Exclusion de /register du filtrage JWT");
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtilities.getToken(request);
        if (token != null && jwtUtilities.validateToken(token)) {
            String login = jwtUtilities.extractUsername(token);
            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(login);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                logger.info("Utilisateur authentifié avec l'identifiant : {}", login);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Injecte l'utilitaire JWT.
     *
     * @param jwtUtilities L'utilitaire JWT à injecter
     */
    @Autowired
    public void setJwtUtilities(JwtUtilities jwtUtilities) {
        this.jwtUtilities = jwtUtilities;
    }

    /**
     * Injecte le service de détails d'utilisateur personnalisé.
     *
     * @param customerUserDetailsService Le service à injecter
     */
    @Autowired
    public void setCustomerUserDetailsService(CustomerUserDetailsService customerUserDetailsService) {
        this.customerUserDetailsService = customerUserDetailsService;
    }
}