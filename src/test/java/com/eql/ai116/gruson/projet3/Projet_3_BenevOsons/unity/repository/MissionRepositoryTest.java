package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.unity.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.*;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.MissionRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.OrganizationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.VolunteerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MissionRepositoryTest {

    /*
    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Test
    @DisplayName("findAllByOrganization_Id() doit retourner les missions pour une organization")
    void testFindAllByOrganization_Id() {
        Organization org = new Organization();
        org.setUserId(1L);  // ou généré automatiquement selon ta config
        organizationRepository.save(org);

        Mission mission = new Mission();
        mission.setOrganization(org);
        Period period = new Period();
        period.setStartDate(LocalDateTime.now().plusDays(1));
        period.setEndDate(LocalDateTime.now().plusDays(2));
        mission.setPeriod(period);
        missionRepository.save(mission);

        List<Mission> missions = missionRepository.findAllByOrganization_Id(org.getUserId());

        assertThat(missions).isNotEmpty();
        assertThat(missions.get(0).getOrganization().getUserId()).isEqualTo(org.getUserId());
    }

    @Test
    @DisplayName("findAllByMissionVolunteerList_Id() doit retourner les missions pour un bénévole")
    void testFindAllByMissionVolunteerList_Id() {
        Volunteer volunteer = new Volunteer();
        volunteer.setUserId(2L);
        volunteerRepository.save(volunteer);

        Organization org = new Organization();
        organizationRepository.save(org);

        Mission mission = new Mission();
        mission.setOrganization(org);

        mission.setMissionVolunteerList(Collections.singletonList(volunteer));

        Period period = new Period();
        period.setStartDate(LocalDateTime.now().plusDays(1));
        period.setEndDate(LocalDateTime.now().plusDays(2));
        mission.setPeriod(period);

        missionRepository.save(mission);

        List<Mission> missions = missionRepository.findAllByMissionVolunteerList_Id(volunteer.getUserId());

        assertThat(missions).isNotEmpty();
        assertThat(missions.get(0).getMissionVolunteerList()).extracting("userId").contains(volunteer.getUserId());
    }

    */
}
