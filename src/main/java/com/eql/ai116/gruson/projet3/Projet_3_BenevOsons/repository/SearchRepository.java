package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Mission,Long> {

    @Query("SELECT m FROM Mission m WHERE m.adress.city = :city")
    List<Mission> findByCity(@Param("city") String city);

    @Query("SELECT m FROM Mission m JOIN m.missionSkillsTypeList st WHERE st.id = :skillTypeId")
    List<Mission> findBySkillTypeId(@Param("skillTypeId") Long skillTypeId);

}
