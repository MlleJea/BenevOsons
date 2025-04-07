package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Skill;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.SkillDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.ResourceNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SpaceService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    /// User display and updatef
    @Transactional
    @GetMapping("/display/{id}")
    public ResponseEntity<Object> displayUser(@PathVariable Long id) {
        try {
            User user = spaceService.displayUser(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            logger.warn("Utilisateur non trouvé avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage de l'utilisateur avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne est survenue.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody RegistrationDto registrationDto) {
        try {
            String message = spaceService.updateUser(id, registrationDto);
            return ResponseEntity.ok(message);
        } catch (ResourceNotFoundException e) {
            logger.warn("Utilisateur non trouvé avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de l'utilisateur avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne est survenue.");
        }
    }

    /// Skills for volunteers
    @GetMapping("/displaySkill/{id}")
    public ResponseEntity<List<Skill>> displaySkill(@PathVariable Long id) {
        try {
            List<Skill> skills = spaceService.displaySkill(id);
            return ResponseEntity.ok(skills);
        } catch (ResourceNotFoundException e) {
            logger.warn("Compétences non trouvées pour l'utilisateur avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of()); // Retourner une liste vide ou un message spécifique
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage des compétences pour l'utilisateur avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/displaySkillTypes")
    public ResponseEntity<List<SkillTypes>> displaySkillTypes() {
        try {
            List<SkillTypes> skillTypes = spaceService.displaySkillTypes();
            return ResponseEntity.ok(skillTypes);
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage des types de compétences.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/displayGrades")
    public ResponseEntity<List<String>> displayGrades() {
        try {
            List<String> grades = spaceService.displayGrades();
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage des notes.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }


    @PostMapping("/addSkill")
    public ResponseEntity<Skill> addSkill(@RequestBody SkillDto skillDto) {
        try {
            Skill newSkill = spaceService.addNewSkill(skillDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);
        } catch (ResourceNotFoundException e) {
            logger.warn("Bénévole non trouvé lors de l'ajout de la compétence : {}", skillDto.getVolunteerId(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout de la compétence.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateSkill")
    public ResponseEntity<Skill> updateSkill(@RequestBody SkillDto skillDto) {
        try {
            Skill updatedSkill = spaceService.updateSkill(skillDto);
            return ResponseEntity.ok(updatedSkill);
        } catch (ResourceNotFoundException e) {
            logger.warn("Compétence ou Bénévole non trouvé lors de la mise à jour de la compétence : {}", skillDto.getSkillId(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la compétence.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteSkill/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        try {
            boolean deleted = spaceService.deleteSkill(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la compétence avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Autowired
    public void setSpaceService(SpaceService spaceService) {
        this.spaceService = spaceService;
    }
}