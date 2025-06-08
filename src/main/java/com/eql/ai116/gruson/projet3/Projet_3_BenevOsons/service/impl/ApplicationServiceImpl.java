package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Application;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.ApplicationStatus;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.ApplicationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.MissionRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.VolunteerRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.ApplicationService;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private NotificationService notificationService;


    public Application applyToMission(Long volunteerId, Long missionId, String message) throws Exception {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new Exception("Bénévole introuvable"));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new Exception("Mission introuvable"));

        LocalDate publicationClosingDate = mission.getPublicationClosingDate();
        LocalDate today = LocalDate.now();

        boolean isActive = publicationClosingDate != null && publicationClosingDate.isAfter(today);

        if (!isActive) {
            throw new Exception("Cette mission n'est plus active");
        }

        if (applicationRepository.existsByVolunteerAndMissionAndStatusNot(
                volunteer, mission, ApplicationStatus.CANCELLED)) {
            throw new Exception("Vous avez déjà postulé à cette mission");
        }

        if (hasScheduleConflict(volunteer, mission)) {
            throw new Exception("Vous avez un conflit d'horaires avec une autre mission acceptée");
        }

        Application application = new Application(volunteer, mission, message,ApplicationStatus.PENDING,today);
        application = applicationRepository.save(application);

        notificationService.createApplicationNotification(application);

        return application;
    }

    /**
     * Accepter une postulation
     */
    public Application acceptApplication(Long applicationId, String responseMessage) throws Exception {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new Exception("Postulation introuvable"));

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new Exception("Cette postulation ne peut plus être acceptée");
        }

        application.accept(responseMessage);
        application = applicationRepository.save(application);

        // Créer une notification pour le bénévole
        notificationService.createApplicationResponseNotification(application, true);

        return application;
    }

    /**
     * Refuser une postulation
     */
    public Application rejectApplication(Long applicationId, String responseMessage) throws Exception {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new Exception("Postulation introuvable"));

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new Exception("Cette postulation ne peut plus être refusée");
        }

        application.reject(responseMessage);
        application = applicationRepository.save(application);

        // Créer une notification pour le bénévole
        notificationService.createApplicationResponseNotification(application, false);

        return application;
    }

    /**
     * Annuler une postulation (par le bénévole)
     */
    public Application cancelApplication(Long applicationId, Long volunteerId) throws Exception {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new Exception("Postulation introuvable"));

        // Vérifier que c'est le bon bénévole
        if (!application.getVolunteer().getUserId().equals(volunteerId)) {
            throw new Exception("Vous n'êtes pas autorisé à annuler cette postulation");
        }

        // RG 16-2 : Vérifier que la postulation peut être annulée
        if (!application.canBeCancelled()) {
            throw new Exception("Cette postulation ne peut plus être annulée");
        }

        application.cancel();
        return applicationRepository.save(application);
    }

    /**
     * Récupérer les postulations d'un bénévole
     */
    public List<Application> getVolunteerApplications(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElse(null);
        if (volunteer == null) return List.of();

        return applicationRepository.findByVolunteerOrderByApplicationDateDesc(volunteer);
    }

    /**
     * Récupérer les postulations pour une mission
     */
    public List<Application> getMissionApplications(Long missionId) {
        Mission mission = missionRepository.findById(missionId).orElse(null);
        if (mission == null) return List.of();

        return applicationRepository.findByMissionOrderByApplicationDateDesc(mission);
    }

    /**
     * Récupérer les postulations pour une organisation
     */
    public List<Application> getOrganizationApplications(Long organizationId) {
        return applicationRepository.findByOrganizationIdOrderByApplicationDateDesc(organizationId);
    }

    /**
     * Récupérer les postulations en attente pour une organisation
     */
    public List<Application> getOrganizationPendingApplications(Long organizationId) {
        return applicationRepository.findByOrganizationIdAndStatusOrderByApplicationDateDesc(
                organizationId, ApplicationStatus.PENDING);
    }

    /**
     * Vérifier s'il y a un conflit d'horaires
     */
    public boolean hasScheduleConflict(Volunteer volunteer, Mission mission) {
        if (mission.getPeriod() == null) return false;

        List<Application> conflictingApplications = applicationRepository
                .findConflictingAcceptedApplications(
                        volunteer,
                        mission.getPeriod().getStartDate(),
                        mission.getPeriod().getEndDate()
                );

        return !conflictingApplications.isEmpty();
    }

    /**
     * Récupérer une postulation spécifique
     */
    public Optional<Application> getApplication(Long applicationId) {
        return applicationRepository.findById(applicationId);
    }

    /**
     * Vérifier si un bénévole a postulé à une mission
     */
    public boolean hasVolunteerApplied(Long volunteerId, Long missionId) {
        Volunteer volunteer = volunteerRepository.findByUserId(volunteerId).orElse(null);
        Mission mission = missionRepository.findById(missionId).orElse(null);

        if (volunteer == null || mission == null) return false;

        return applicationRepository.existsByVolunteerAndMissionAndStatusNot(
                volunteer, mission, ApplicationStatus.CANCELLED);
    }
}