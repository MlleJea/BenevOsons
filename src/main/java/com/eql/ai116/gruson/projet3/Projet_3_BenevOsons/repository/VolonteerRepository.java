package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Skill;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VolonteerRepository extends JpaRepository<Volunteer,Long> {

    @Query("SELECT v.volunteerSkillsList FROM Volunteer v WHERE v.userId = :userId")
    List<Skill> findUserSkillsByUserId(@Param("userId") Long userId);
}
