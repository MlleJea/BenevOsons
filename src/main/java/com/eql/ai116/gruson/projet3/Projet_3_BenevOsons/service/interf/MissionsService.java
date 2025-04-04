package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MissionsService {

    List<Mission> displayAllMissions(Long id);
    Mission addMission(Long id, Mission mission);
    Mission updateMission(Long id, Mission mission);
    void deleteMission(Long id, Long missionId);
}
