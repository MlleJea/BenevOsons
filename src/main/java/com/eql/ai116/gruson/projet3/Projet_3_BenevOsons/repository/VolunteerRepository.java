package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Skill;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des bénévoles.
 *
 * Cette interface étend JpaRepository et fournit des méthodes personnalisées
 * pour accéder aux données spécifiques aux bénévoles, notamment leurs compétences.
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer,Long> {

    /**
     * Récupère toutes les compétences d'un bénévole par son identifiant utilisateur.
     *
     * Cette méthode utilise une requête JPQL pour extraire la liste des compétences
     * associées à un bénévole spécifique.
     *
     * @param userId L'identifiant unique de l'utilisateur bénévole
     * @return Liste des compétences du bénévole. Retourne une liste vide
     *         si le bénévole n'a pas de compétences ou n'existe pas.
     */
    @Query("SELECT v.volunteerSkillsList FROM Volunteer v WHERE v.userId = :userId")
    List<Skill> findUserSkillsByUserId(@Param("userId") Long userId);

    Optional<Volunteer> findByUserId(Long userId);

}