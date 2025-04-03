package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Skill;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.SkillDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpaceService {

    /// User
    User displayUser(Long id);

    ResponseEntity<Object> updateUser(Long id, RegistrationDto registrationDto);

    /// Boolean
    List<Skill> displaySkill(Long id);

    List<SkillTypes> displaySkillTypes();

    Skill addNewSkill(SkillDto skillDto);

    Skill updateSkill(SkillDto skillDto);

    Boolean deleteSkill(Long id);

}
