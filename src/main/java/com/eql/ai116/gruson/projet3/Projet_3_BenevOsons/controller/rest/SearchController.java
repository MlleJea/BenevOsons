package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rest/search")
@CrossOrigin(origins = "*")
public class SearchController {

    private Logger logger = LogManager.getLogger();

    SearchService searchService;


    @GetMapping("/by/city/{city}")
    public ResponseEntity<Object> searchByCity (@PathVariable String city){
        try {
            List<Mission> missions = searchService.searchByCity(city);
            return ResponseEntity.ok(missions);
        } catch (ResourceNotFoundException e){
            logger.warn("Aucune mission avec cette ville trouvée" + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            logger.error("Erreur lors de la recherche de mission à la ville : {}", city, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/by/skills/{skillId}")
    public ResponseEntity<Object> searchByCity (@PathVariable Long skillId){
        try {
            List<Mission> missions = searchService.searchBySkills(skillId);
            return ResponseEntity.ok(missions);
        } catch (ResourceNotFoundException e){
            logger.warn("Aucune mission avec cette compétence trouvée" + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            logger.error("Erreur lors de la recherche de mission avec la compétence: {}", skillId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}
