package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.MissionSearchCriteriaDTO;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.ResourceNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SearchController.java
 * Contrôleur REST qui gère les opérations de recherche de missions.
 *
 * Ce contrôleur expose des endpoints permettant de:
 *  Rechercher des missions en fonction de critères spécifiques
 *
 * @author Jeanne GRUSON
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "${front.url}")
@RequestMapping("/api/rest/search")
public class SearchController {

    private Logger logger = LogManager.getLogger();

    SearchService searchService;

    /**
     * Recherche des missions selon les critères fournis.
     *
     * @param criteria Objet contenant les critères de recherche
     * @return ResponseEntity contenant la liste des missions correspondantes ou un message d'erreur
     */
    @PostMapping("/filter")
    public ResponseEntity<Object> searchWithFilters(@RequestBody MissionSearchCriteriaDTO criteria) {
        logger.info("Recherche de missions avec critères");
        try {
            List<Mission> missions = searchService.searchMissionsWithFilters(criteria);
            if (missions.isEmpty()) {
                throw new ResourceNotFoundException("Aucune mission ne correspond aux critères.");
            }
            return ResponseEntity.ok(missions);
        } catch (ResourceNotFoundException e) {
            logger.warn("Aucun résultat trouvé: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur lors de la recherche: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la recherche.");
        }
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Object> getVolunteerAdress(@PathVariable Long id){
        logger.info("Récupération des adresses pour l'utilisateur: {}", id);
        try {
            List<Address> addresses = searchService.getVolunteerAddresses(id);
            return ResponseEntity.ok(addresses);
        } catch (ResourceNotFoundException e) {
            logger.warn("Adresses non trouvées pour l'utilisateur {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des adresses pour l'utilisateur {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}