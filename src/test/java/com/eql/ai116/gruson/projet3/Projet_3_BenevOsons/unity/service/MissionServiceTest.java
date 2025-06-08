package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.unity.service;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.*;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.UserNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.MissionRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.OrganizationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SkillTypesRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl.AddressServiceImpl;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl.MissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class MissionServiceTest {

    @InjectMocks
    private MissionServiceImpl missionService;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private AddressServiceImpl addressService;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private SkillTypesRepository skillTypesRepository;

    private Mission mission;
    private Period period;
    private Organization org;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        period = new Period();
        period.setStartDate(LocalDateTime.now().plusDays(3));
        period.setEndDate(LocalDateTime.now().plusDays(5));

        mission = new Mission();
        mission.setPeriod(period);
        mission.setAddress(new Address());

        org = new Organization();
        org.setUserId(1L);
    }

    @Test
    void testDisplayAllMissionsForOrganization() {
        when(missionRepository.findAllByOrganization_UserId(1L)).thenReturn(List.of(mission));

        List<Mission> result = missionService.displayAllMissionsForOrganization(1L);

        assertEquals(1, result.size());
        verify(missionRepository).findAllByOrganization_UserId(1L);
    }

    @Test
    void testDisplayAllMissionsForVolunteer() {
        when(missionRepository.findAllMissionsByVolunteerUserId(2L)).thenReturn(List.of(mission));

        List<Mission> result = missionService.displayAllMissionsForVolunteer(2L);

        assertEquals(1, result.size());
        verify(missionRepository).findAllMissionsByVolunteerUserId(2L);
    }

    @Test
    void testAddMission_Success() throws Exception {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(org));
        when(addressService.addressWithLatLon(any())).thenReturn(new Address());
        when(missionRepository.findAllByOrganization_UserId(1L)).thenReturn(new ArrayList<>());
        when(missionRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Mission result = missionService.addMission(1L, mission);

        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getPublicationDate());
    }

    @Test
    void testAddMission_TooSoon() {
        Period customPeriod = new Period();
        customPeriod.setStartDate(LocalDateTime.now().plusHours(10));
        customPeriod.setEndDate(LocalDateTime.now().plusDays(2));
        mission.setPeriod(customPeriod);

        when(organizationRepository.findById(1L)).thenReturn(Optional.of(org));
        when(missionRepository.findAllByOrganization_UserId(1L)).thenReturn(new ArrayList<>());
        when(addressService.addressWithLatLon(any())).thenReturn(new Address());

        assertThrows(IllegalArgumentException.class, () -> missionService.addMission(1L, mission));
    }

    @Test
    void testAddMission_StartAfterEnd() {
        Period invalidPeriod = new Period();
        invalidPeriod.setStartDate(LocalDateTime.now().plusDays(4));
        invalidPeriod.setEndDate(LocalDateTime.now().plusDays(2));
        mission.setPeriod(invalidPeriod);

        when(organizationRepository.findById(1L)).thenReturn(Optional.of(org));
        when(missionRepository.findAllByOrganization_UserId(1L)).thenReturn(new ArrayList<>());
        when(addressService.addressWithLatLon(any())).thenReturn(new Address());

        assertThrows(IllegalArgumentException.class, () -> missionService.addMission(1L, mission));
    }

    @Test
    void testAddMission_TooManyActive() {
        List<Mission> existing = new ArrayList<>(Collections.nCopies(10, new Mission()));
        when(missionRepository.findAllByOrganization_UserId(1L)).thenReturn(existing);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(org));
        when(addressService.addressWithLatLon(any())).thenReturn(new Address());

        assertThrows(IllegalStateException.class, () -> missionService.addMission(1L, mission));
    }
}
