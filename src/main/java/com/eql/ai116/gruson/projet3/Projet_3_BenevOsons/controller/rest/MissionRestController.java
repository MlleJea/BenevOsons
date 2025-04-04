package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;


import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.MissionsService;
import org.apache.coyote.Response;
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

    Logger logger = LogManager.getLogger();

    MissionsService missionsService;

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

    @PostMapping("/add/{id}")
    public ResponseEntity<Object> addMission(@PathVariable Long id, @RequestBody Mission mission){
        try {
            Mission okMission = missionsService.addMission(id, mission);
            return ResponseEntity.ok(okMission);
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout d'une mission pour l'utilisateur " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

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
