package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Application;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;

import java.util.List;

public interface ApplicationService {
    Application applyToMission(Long volunteerId, Long missionId, String message) throws Exception;
    Application acceptApplication(Long applicationId, String responseMessage) throws Exception;
    Application rejectApplication(Long applicationId, String responseMessage) throws Exception;
    Application cancelApplication(Long applicationId, Long volunteerId) throws Exception;
    List<Application> getVolunteerApplications(Long volunteerId);
    List<Application> getMissionApplications(Long missionId);
    List<Application> getOrganizationApplications(Long organizationId);
    List<Application> getOrganizationPendingApplications(Long organizationId);
    boolean hasScheduleConflict(Volunteer volunteer, Mission mission);
    boolean hasVolunteerApplied(Long volunteerId, Long missionId);
}
