package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillTypesRepository extends JpaRepository<SkillTypes,Long> {

    Boolean existsByLabel(String label);
    SkillTypes findByLabel(String label);

}
