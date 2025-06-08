package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.security;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Implémentation personnalisée de UserDetails pour l'intégration avec Spring Security.
 *
 * Cette classe adapte l'entité User du domaine métier aux exigences de
 * l'interface UserDetails de Spring Security, permettant une authentification
 * et autorisation personnalisées.
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
public class CustomUserDetails implements UserDetails {

    /**
     * L'entité utilisateur encapsulée.
     */
    private final User user;

    /**
     * Constructeur pour créer une instance CustomUserDetails.
     *
     * @param user L'entité utilisateur à encapsuler
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Retourne les autorités accordées à l'utilisateur.
     *
     * Convertit le rôle de l'utilisateur en une collection d'autorités
     * compréhensible par Spring Security.
     *
     * @return Collection des autorités de l'utilisateur, ou une collection vide
     *         si l'utilisateur ou son rôle est null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user == null || user.getRole() == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRoleName()));
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe hashé de l'utilisateur
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retourne le nom d'utilisateur utilisé pour l'authentification.
     *
     * Dans ce système, l'adresse email sert de nom d'utilisateur.
     *
     * @return L'adresse email de l'utilisateur
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Indique si le compte de l'utilisateur a expiré.
     *
     * @return true car les comptes n'expirent pas dans ce système
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indique si le compte de l'utilisateur est verrouillé.
     *
     * @return true car les comptes ne sont pas verrouillés dans ce système
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indique si les identifiants de l'utilisateur ont expiré.
     *
     * @return true car les identifiants n'expirent pas dans ce système
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indique si l'utilisateur est activé.
     *
     * @return true car tous les utilisateurs sont considérés comme activés
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Retourne l'entité utilisateur encapsulée.
     *
     * @return L'objet User associé à ces détails d'utilisateur
     */
    public User getUser() {
        return user;
    }
}