package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Skill;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.SkillDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SpaceService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/rest/space")
public class SpaceRestController {

    private Logger logger = LogManager.getLogger();

    private SpaceService spaceService;

    /// User display and update
    @Transactional
    @GetMapping("/display/{id}")
    public Object displayUser(@PathVariable Long id) {
        return spaceService.displayUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody RegistrationDto registrationDto) {
        return spaceService.updateUser(id, registrationDto);
    }

    /// Skills for volunteers
    @GetMapping("/displaySkill/{id}")
    public List<Skill> displaySkill(@PathVariable Long id) {
        return spaceService.displaySkill(id);
    }

    @GetMapping("/displaySkillTypes")
    public List<SkillTypes> displaySkillTypes() {
        return spaceService.displaySkillTypes();
    }

    @PostMapping("/addSkill")
    public Skill addSkill(@RequestBody SkillDto skillDto) {
        return spaceService.addNewSkill(skillDto);
    }

    @PutMapping("/updateSkill")
    public Skill updateSkill(@RequestBody SkillDto skillDto) {
        return spaceService.updateSkill(skillDto);
    }

    @DeleteMapping("/deleteSkill/{id}")
    public Boolean deleteSkill(@PathVariable Long id) {
        return spaceService.deleteSkill(id);
    }

    @Autowired
    public void setSpaceService(SpaceService spaceService) {
        this.spaceService = spaceService;
    }
}
