package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillTypesRepository extends JpaRepository<SkillTypes,Long> {

    Boolean existsByLabel(String label);
    Optional<SkillTypes> findByLabel(String label);

}
