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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /////////////////
    /// Attributs ///
    /////////////////

    private static final Logger logger = LogManager.getLogger();

    /** Injecté par son mutateur */
    private JwtUtilities jwtUtilities ;
    /** Injecté par son mutateur */
    private CustomerUserDetailsService customerUserDetailsService ;

    ////////////////
    /// Méthodes ///
    ////////////////

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("Requête reçue");

        if (request.getRequestURI().contains("/api/rest/security/register")) {
            logger.info("Exclusion de /register du filtrage JWT");
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtilities.getToken(request) ;
        if (token != null && jwtUtilities.validateToken(token)) {
            String login = jwtUtilities.extractUsername(token);
            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(login);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername() ,null , userDetails.getAuthorities());
                logger.info("Utilisateur authentifié avec l'identifiant : {}", login);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }

    /////////////////
    /// Mutateurs ///
    /////////////////

    @Autowired
    public void setJwtUtilities(JwtUtilities jwtUtilities) {
        this.jwtUtilities = jwtUtilities;
    }
    @Autowired
    public void setCustomerUserDetailsService(CustomerUserDetailsService customerUserDetailsService) {
        this.customerUserDetailsService = customerUserDetailsService;
    }

}
