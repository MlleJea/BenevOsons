package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.ReferentielService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/rest/referentiel")
@CrossOrigin(origins = "*")
public class ReferentielController {

    private Logger logger = LogManager.getLogger();

    private ReferentielService referentielService;


    @GetMapping("/displaySkillTypes")
    public ResponseEntity<List<SkillTypes>> displaySkillTypes() {
        try {
            List<SkillTypes> skillTypes = referentielService.displaySkillTypes();
            return ResponseEntity.ok(skillTypes);
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage des types de compétences.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/displayGrades")
    public ResponseEntity<List<String>> displayGrades() {
        try {
            List<String> grades = referentielService.displayGrades();
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage des notes.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @Autowired
    public void setReferentielService(ReferentielService referentielService) {
        this.referentielService = referentielService;
    }
}
