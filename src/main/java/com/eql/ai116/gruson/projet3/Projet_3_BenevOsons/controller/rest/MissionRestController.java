/**
 * MissionRestController.java
 * Contrôleur REST qui gère les opérations CRUD pour les missions des utilisateurs.
 *
 * Ce contrôleur expose des endpoints permettant de:
 *  Afficher les missions d'un utilisateur
 *  Ajouter une nouvelle mission
 *  Mettre à jour une mission existante
 *  Supprimer une mission
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */

package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;


import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.MissionsService;
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
@RequestMapping("api/rest/mission")
@CrossOrigin(origins = "*")
public class MissionRestController {

    private static final Logger logger = LogManager.getLogger();

    private MissionsService missionsService;

    /**
     * Récupère toutes les missions d'un utilisateur spécifique.
     *
     * @param id Identifiant de l'utilisateur
     * @return ResponseEntity contenant la liste des missions ou une erreur
     */
    @GetMapping("/displayMyMissions/{id}")
    public ResponseEntity<Object> displayMyMissions(@PathVariable Long id){
        try {
            List<Mission> missions = missionsService.displayAllMissions(id);
            return ResponseEntity.ok(missions);
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage des missions pour l'utilisateur " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    /**
     * Ajoute une nouvelle mission pour un utilisateur spécifique.
     *
     * @param id Identifiant de l'utilisateur
     * @param mission Objet Mission à ajouter
     * @return ResponseEntity contenant la mission créée ou une erreur
     */
    @PostMapping("/add/{id}")
    public ResponseEntity<Object> addMission(@PathVariable Long id, @RequestBody Mission mission){
        System.out.println(mission);
        try {
            Mission okMission = missionsService.addMission(id, mission);
            return ResponseEntity.ok(okMission);
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout d'une mission pour l'utilisateur " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    /**
     * Met à jour une mission existante pour un utilisateur spécifique.
     *
     * @param id Identifiant de l'utilisateur
     * @param mission Objet Mission mis à jour
     * @return ResponseEntity contenant la mission mise à jour ou une erreur
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateMission(@PathVariable Long id, @RequestBody Mission mission){
        try {
            Mission updateMission =missionsService.updateMission(id, mission);
            return ResponseEntity.ok(updateMission);
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la mission pour l'utilisateur " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    /**
     * Supprime une mission spécifique d'un utilisateur.
     *
     * @param id Identifiant de l'utilisateur
     * @param missionId Identifiant de la mission à supprimer
     * @return ResponseEntity contenant un message de confirmation ou une erreur
     */
    @DeleteMapping("/delete/{id}/{missionId}")
    public ResponseEntity<Object> deleteMission(@PathVariable Long id, @PathVariable Long missionId){
        try {
            missionsService.deleteMission(id, missionId);
            return ResponseEntity.ok("Mission supprimée");
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la mission " + missionId + " pour l'utilisateur " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @Autowired
    public void setMissionsService(MissionsService missionsService) {
        this.missionsService = missionsService;
    }
}
