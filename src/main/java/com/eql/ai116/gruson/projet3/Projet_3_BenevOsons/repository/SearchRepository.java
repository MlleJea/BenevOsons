package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la recherche de missions avec filtres.
 * <p>
 * Cette interface étend JpaRepository et fournit des méthodes de recherche
 * personnalisées pour les missions en fonction de différents critères.
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
@Repository
public interface SearchRepository extends JpaRepository<Mission, Long> {

    /**
     * Recherche des missions en appliquant des filtres optionnels.
     * <p>
     * Cette méthode permet de rechercher des missions en filtrant par :
     * Ville (recherche partielle insensible à la casse)
     * Types de compétences requis
     * Date de début minimum
     * Date de fin maximum
     * <p>
     * La requête inclut également une condition pour ne retourner que les missions
     * qui sont soit futures, soit actuellement en cours.
     *
     * @param city         Le nom de la ville à rechercher (peut être null pour ignorer ce filtre).
     *                     La recherche est insensible à la casse et utilise une correspondance partielle.
     * @param skillTypeIds Liste des identifiants des types de compétences requis
     *                     (peut être null pour ignorer ce filtre)
     * @param startDate    Date de début minimum pour filtrer les missions
     *                     (peut être null pour ignorer ce filtre)
     * @param endDate      Date de fin maximum pour filtrer les missions
     *                     (peut être null pour ignorer ce filtre)
     * @return Liste des missions correspondantes aux critères de recherche.
     * Retourne une liste vide si aucune mission ne correspond.
     * Retourne une liste vide si aucune mission ne correspond.
     */
    @Query("""
                SELECT DISTINCT m FROM Mission m
                JOIN m.missionSkillsTypeList st
                WHERE (:city IS NULL OR LOWER(m.address.city) LIKE LOWER(CONCAT('%', :city, '%')))
                  AND (:skillTypeIds IS NULL OR st.idSkillType IN :skillTypeIds)
                  AND (:startDate IS NULL OR m.period.startDate >= :startDate)
                  AND (:endDate IS NULL OR m.period.endDate <= :endDate)
                  AND (m.period.startDate >= CURRENT_TIMESTAMP OR (m.period.startDate <= CURRENT_TIMESTAMP AND m.period.endDate >= CURRENT_TIMESTAMP))
            """)
    List<Mission> findMissionsWithFilters(
            @Param("city") String city,
            @Param("skillTypeIds") List<Long> skillTypeIds,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}