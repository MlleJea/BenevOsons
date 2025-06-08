package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des utilisateurs.
 *
 * Cette interface fournit les méthodes CRUD de base ainsi que des requêtes
 * personnalisées pour la gestion des utilisateurs et de leurs addresses.
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Vérifie si un utilisateur existe avec l'addresse email donnée.
     *
     * @param email L'addresse email à vérifier
     * @return true si un utilisateur avec cette addresse email existe, false sinon
     */
    Boolean existsByEmail(String email);

    /**
     * Recherche un utilisateur par son addresse email.
     *
     * @param email L'addresse email de l'utilisateur à rechercher
     * @return L'utilisateur correspondant à l'addresse email, ou null si non trouvé
     */
    User findByEmail(String email);

    /**
     * Recherche un utilisateur par son identifiant.
     *
     * Cette méthode redéfinit explicitement la méthode héritée de JpaRepository
     * pour une meilleure lisibilité du code.
     *
     * @param id L'identifiant unique de l'utilisateur
     * @return Un Optional contenant l'utilisateur s'il existe, Optional.empty() sinon
     */
    Optional<User> findById(Long id);

    /**
     * Récupère toutes les addresses associées à un utilisateur.
     *
     * Cette méthode utilise une requête JPQL pour récupérer la liste des addresses
     * d'un utilisateur spécifique.
     *
     * @param userId L'identifiant unique de l'utilisateur
     * @return Liste des addresses de l'utilisateur. Retourne une liste vide
     *         si l'utilisateur n'a pas d'addresses ou n'existe pas.
     */
    @Query("SELECT u.userAddressList FROM User u WHERE u.userId = :userId")
    List<Address> findUserAddressesByUserId(@Param("userId") Long userId);
}