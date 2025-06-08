package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Classe utilitaire pour la gestion des tokens JWT.
 *
 * Cette classe fournit toutes les fonctionnalités nécessaires pour :
 *  Générer des tokens JWT
 *  Valider des tokens JWT
 *  Extraire des informations des tokens
 *  Gérer l'expiration des tokens
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
@Component
public class JwtUtilities {

    /**
     * Logger pour enregistrer les erreurs et événements.
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Clé secrète pour signer les tokens JWT.
     * Récupérée depuis les propriétés de configuration.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Durée d'expiration des tokens JWT en millisecondes.
     * Récupérée depuis les propriétés de configuration.
     */
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    /**
     * Extrait le nom d'utilisateur (sujet) du token JWT.
     *
     * @param token Le token JWT à analyser
     * @return Le nom d'utilisateur contenu dans le token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait toutes les claims (revendications) du token JWT.
     *
     * @param token Le token JWT à analyser
     * @return Objet Claims contenant toutes les informations du token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Extrait une claim spécifique du token JWT en utilisant un résolveur de claims.
     *
     * @param <T> Le type de la claim à extraire
     * @param token Le token JWT à analyser
     * @param claimsResolver Fonction pour résoudre la claim spécifique
     * @return La valeur de la claim extraite
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait la date d'expiration du token JWT.
     *
     * @param token Le token JWT à analyser
     * @return La date d'expiration du token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Valide un token JWT pour un utilisateur spécifique.
     *
     * Vérifie que le token appartient bien à l'utilisateur et n'est pas expiré.
     *
     * @param token Le token JWT à valider
     * @param userDetails Les détails de l'utilisateur à vérifier
     * @return true si le token est valide pour cet utilisateur, false sinon
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Vérifie si un token JWT est expiré.
     *
     * @param token Le token JWT à vérifier
     * @return true si le token est expiré, false sinon
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Génère un nouveau token JWT pour un utilisateur.
     *
     * Le token contient l'email de l'utilisateur comme sujet, son rôle comme claim,
     * et est configuré avec une date d'expiration basée sur la configuration.
     *
     * @param email L'adresse email de l'utilisateur
     * @param role Le rôle de l'utilisateur
     * @return Le token JWT généré
     */
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.MILLIS)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    /**
     * Valide un token JWT en vérifiant sa structure et sa signature.
     *
     * Cette méthode capture et log différents types d'exceptions liées
     * à la validation des tokens JWT.
     *
     * @param token Le token JWT à valider
     * @return true si le token est valide, false en cas d'erreur
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Signature JWT invalide.", e);
        } catch (MalformedJwtException e) {
            logger.error("Token JWT invalide.", e);
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expiré.", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT non supporté.", e);
        } catch (IllegalArgumentException e) {
            logger.error("Arguments du token JWT invalides.", e);
        }
        return false;
    }

    /**
     * Extrait le token JWT de l'en-tête Authorization de la requête HTTP.
     *
     * Recherche un en-tête Authorization contenant un token Bearer et
     * extrait la partie token (après "Bearer ").
     *
     * @param httpServletRequest La requête HTTP à analyser
     * @return Le token JWT si présent, null sinon
     */
    public String getToken(HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }
}