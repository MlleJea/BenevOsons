package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Configuration principale de la sécurité Spring Security.
 *
 * Cette classe configure :
 *  La chaîne de filtres de sécurité
 *  Les règles d'autorisation pour les différentes routes
 *  L'authentification JWT
 *  L'encodage des mots de passe
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Filtre d'authentification JWT personnalisé.
     * Injecté par son mutateur.
     */
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${front.url}")
    private String frontUrl;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontUrl));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configure la chaîne de filtres de sécurité principale.
     *
     * Cette méthode définit :
     * La désactivation de CSRF (non nécessaire pour les APIs REST)
     *  La politique de session STATELESS pour JWT
     *  Les règles d'autorisation pour chaque endpoint
     *  L'ordre des filtres d'authentification
     *
     * @param http L'objet HttpSecurity à configurer
     * @return La chaîne de filtres configurée
     * @throws Exception En cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/error").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                            // Accès public
                            .requestMatchers("/api/rest/security/**").permitAll()
                            .requestMatchers("/api/rest/referentiel/**").permitAll()

                            // VOLUNTEER uniquement
                            .requestMatchers("/api/rest/search/**").hasAuthority("VOLUNTEER")

                            // VOLUNTEER et ORGANIZATION
                            .requestMatchers("/api/rest/space/**").hasAnyAuthority("VOLUNTEER", "ORGANIZATION")
                            .requestMatchers("/api/rest/mission/**").hasAnyAuthority("VOLUNTEER", "ORGANIZATION")

                            // Tout le reste doit être authentifié
                            .anyRequest().authenticated();
                });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }

    /**
     * Configure le gestionnaire d'authentification.
     *
     * @param authenticationConfiguration La configuration d'authentification
     * @return Le gestionnaire d'authentification configuré
     * @throws Exception En cas d'erreur de configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configure l'encodeur de mots de passe.
     *
     * Utilise BCryptPasswordEncoder pour un hachage sécurisé des mots de passe
     * avec salt automatique et résistance aux attaques par force brute.
     *
     * @return L'encodeur de mots de passe BCrypt configuré
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Injecte le filtre d'authentification JWT.
     *
     * @param jwtAuthenticationFilter Le filtre JWT à injecter
     */
    @Autowired
    public void setJwtAuthenticationFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
}