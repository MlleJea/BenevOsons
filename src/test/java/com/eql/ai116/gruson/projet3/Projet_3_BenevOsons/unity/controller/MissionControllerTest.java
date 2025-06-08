package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.unity.controller;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.controller.rest.MissionController;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.UserNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.MissionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MissionControllerTest {

    @InjectMocks
    private MissionController missionController;

    @Mock
    private MissionsService missionsService;

    private Mission mission;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mission = new Mission();
    }

    @Test
    void testDisplayOrganizationMissions() {
        when(missionsService.displayAllMissionsForOrganization(1L)).thenReturn(List.of(mission));

        ResponseEntity<Object> response = missionController.displayOrganizationMissions(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(mission), response.getBody());
    }

    @Test
    void testDisplayVolunteerMissions() {
        when(missionsService.displayAllMissionsForVolunteer(2L)).thenReturn(List.of(mission));

        ResponseEntity<Object> response = missionController.displayVolunteerMissions(2L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(mission), response.getBody());
    }

    @Test
    void testAddMission() throws Exception {
        when(missionsService.addMission(eq(1L), any(Mission.class))).thenReturn(mission);

        ResponseEntity<Object> response = missionController.addMission(1L, mission);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mission, response.getBody());
    }

    @Test
    void testUpdateMission() {
        when(missionsService.updateMission(eq(1L), any(Mission.class))).thenReturn(mission);

        ResponseEntity<Object> response = missionController.updateMission(1L, mission);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mission, response.getBody());
    }

    @Test
    void testDeleteMission() {
        doNothing().when(missionsService).deleteMission(1L, 100L);

        ResponseEntity<Object> response = missionController.deleteMission(1L, 100L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mission supprimée", response.getBody());
    }
}