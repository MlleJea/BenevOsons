package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.MissionRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.MissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MissionServiceImpl implements MissionsService {

    MissionRepository missionRepository;


    @Override
    public List<Mission> displayAllMissions(Long id) {
         return missionRepository.findAll();
    }

    @Override
    public Mission addMission(Long id, Mission mission) {
        mission.setPublicationDate(LocalDate.now());

        return missionRepository.save(mission);
    }

    @Override
    public Mission updateMission(Long id, Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public void deleteMission(Long id, Long missionId) {
        missionRepository.deleteById(missionId);
    }

    /// Setter
    @Autowired
    public void setMissionRepository(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }
}
