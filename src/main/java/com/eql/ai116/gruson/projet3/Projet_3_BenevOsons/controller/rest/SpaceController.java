/**
 * SpaceController.java
 * Contrôleur REST qui gère les opérations de l'espace utilisateur.
 *
 * Ce contrôleur expose des endpoints permettant de:
 *  Afficher et mettre à jour les informations d'un utilisateur
 *  Gérer les compétences d'un bénévole (afficher, ajouter, mettre à jour, supprimer)
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */

package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Skill;
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

@RestController
@CrossOrigin(origins = "${front.url}")
@RequestMapping("api/rest/space")
public class SpaceController {

    private static final Logger logger = LogManager.getLogger();

    private SpaceService spaceService;

    /**
     * Affiche les informations d'un utilisateur spécifique.
     *
     * @param id Identifiant de l'utilisateur
     * @return ResponseEntity contenant les informations de l'utilisateur ou un message d'erreur
     */
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param id Identifiant de l'utilisateur
     * @param registrationDto Objet contenant les nouvelles informations de l'utilisateur
     * @return ResponseEntity contenant un message de confirmation ou d'erreur
     */
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Affiche les compétences d'un bénévole spécifique.
     *
     * @param id Identifiant du bénévole
     * @return ResponseEntity contenant la liste des compétences ou une liste vide en cas d'erreur
     */
    @GetMapping("/displaySkill/{id}")
    public ResponseEntity<List<Skill>> displaySkill(@PathVariable Long id) {
        try {
            List<Skill> skills = spaceService.displaySkill(id);
            return ResponseEntity.ok(skills);
        } catch (ResourceNotFoundException e) {
            logger.warn("Compétences non trouvées pour l'utilisateur avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage des compétences pour l'utilisateur avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }


    /**
     * Ajoute une nouvelle compétence à un bénévole.
     *
     * @param skillDto Objet contenant les informations de la compétence
     * @param id Identifiant du bénévole
     * @return ResponseEntity contenant la compétence créée ou null en cas d'erreur
     */
    @PostMapping("/addSkill/{id}")
    public ResponseEntity<Skill> addSkill(@RequestBody SkillDto skillDto, @PathVariable Long id) {

        logger.error(skillDto);
        try {
            Skill newSkill = spaceService.addNewSkill(skillDto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);
        } catch (ResourceNotFoundException e) {
            logger.warn("Bénévole non trouvé lors de l'ajout de la compétence : {}", skillDto.getVolunteerId(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout de la compétence.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Met à jour une compétence existante.
     *
     * @param skillDto Objet contenant les nouvelles informations de la compétence
     * @return ResponseEntity contenant la compétence mise à jour ou null en cas d'erreur
     */
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

    /**
     * Supprime une compétence spécifique.
     *
     * @param id Identifiant de la compétence à supprimer
     * @return ResponseEntity avec un statut HTTP 204 en cas de succès ou 404/500 en cas d'erreur
     */
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