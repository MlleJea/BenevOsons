package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Organization;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Period;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.UserNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.MissionRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.OrganizationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SkillTypesRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.MissionsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MissionServiceImpl implements MissionsService {

    Logger logger = LogManager.getLogger();

    /// Attributs
    MissionRepository missionRepository;
    AdressServiceImpl adressService;
    OrganizationRepository organizationRepository;
    SkillTypesRepository skillTypesRepository;


    @Override
    public List<Mission> displayAllMissions(Long id) {
         return missionRepository.findAll();
    }

    @Override
    public Mission addMission(Long id, Mission mission) throws UserNotFoundException {
        mission.setPublicationDate(LocalDate.now());

        Adress treatedAdress = adressService.adressWithLatLon(mission.getAdress());
        mission.setAdress(treatedAdress);

        Optional<Organization> optionalOrg = organizationRepository.findById(id);
        Organization orga = optionalOrg.orElseThrow(() -> {
            logger.info("Organisation avec l'id {} non trouvée", id);
            return new UserNotFoundException("Organisation avec l'id " + id + " non trouvée.");
        });

        List<SkillTypes> skillTypesToAssociate = new ArrayList<>();
        List<SkillTypes> skillTypesFromMission = mission.getMissionSkillsTypeList();

        if (skillTypesFromMission != null) {
            for (SkillTypes skillTypeToVerify : skillTypesFromMission) {
                String skillLabel = skillTypeToVerify.getLabel();
                Optional<SkillTypes> existingSkillTypeOptional = skillTypesRepository.findByLabel(skillLabel);

                SkillTypes skillType;
                if (existingSkillTypeOptional.isPresent()) {
                    skillType = existingSkillTypeOptional.get();
                } else {
                    SkillTypes newSkillType = new SkillTypes();
                    newSkillType.setLabel(skillLabel);
                    skillType = skillTypesRepository.save(newSkillType);
                }
                skillTypesToAssociate.add(skillType);
            }
            mission.setMissionSkillsTypeList(skillTypesToAssociate);
        } else {
            mission.setMissionSkillsTypeList(Collections.emptyList());
        }

        mission.setOrganization(orga);

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

    @Autowired
    public void setAdressService(AdressServiceImpl adressService) {
        this.adressService = adressService;
    }

    @Autowired
    public void setOrganizationRepository(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Autowired
    public void setSkillTypesRepository(SkillTypesRepository skillTypesRepository) {
        this.skillTypesRepository = skillTypesRepository;
    }
}
