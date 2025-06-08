package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Organization;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Period;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.UserNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.MissionRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.OrganizationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SkillTypesRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.MissionsService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MissionServiceImpl implements MissionsService {

    Logger logger = LogManager.getLogger();

    /// Attributs
    MissionRepository missionRepository;
    AddressServiceImpl addressService;
    OrganizationRepository organizationRepository;
    SkillTypesRepository skillTypesRepository;


    @Override
    public List<Mission> displayAllMissionsForOrganization(Long organizationId) {
         return missionRepository.findAllByOrganization_UserId(organizationId);
    }
    @Override
    public List<Mission> displayAllMissionsForVolunteer(Long volunteerId) {
        return missionRepository.findAllMissionsByVolunteerUserId(volunteerId);
    }

    @Transactional
    @Override
    public Mission addMission(Long id, Mission mission) throws UserNotFoundException {

        logger.info(mission);

        Period period = mission.getPeriod();

        if (period.getStartDate().isAfter(period.getEndDate())) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }

        if (period.getStartDate().isBefore(LocalDateTime.now().plusHours(48))) {
            throw new IllegalArgumentException("La date de début doit être au moins 48h après la date de publication.");
        }

        List<Mission> existingMissions = missionRepository.findAllByOrganization_UserId(id);
        if (existingMissions != null && existingMissions.size() >= 10) {
            throw new IllegalStateException("Une association ne peut pas avoir plus de 10 missions actives.");
        }

        mission.setPublicationDate(LocalDate.now());

        Address treatedAddress = addressService.addressWithLatLon(mission.getAddress());
        mission.setAddress(treatedAddress);

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
    public void setAddressService(AddressServiceImpl addressService) {
        this.addressService = addressService;
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
