package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Mission,Long> {

    @Query("""
    SELECT DISTINCT m FROM Mission m
    JOIN m.missionSkillsTypeList st
    WHERE (:city IS NULL OR LOWER(m.adress.city) LIKE LOWER(CONCAT('%', :city, '%')))
      AND (:skillTypeIds IS NULL OR st.idSkillType IN :skillTypeIds)
      AND (:startDate IS NULL OR m.period.startDate >= :startDate)
      AND (:endDate IS NULL OR m.period.endDate <= :endDate)
      AND (m.period.startDate >= CURRENT_TIMESTAMP OR (m.period.startDate <= CURRENT_TIMESTAMP AND m.period.endDate >= CURRENT_TIMESTAMP))
""")
    List<Mission> findMissionsWithFilters(
            @Param("city") String city,
            @Param("skillTypeIds") List<Long> skillTypeIds,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
