package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;


import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Application;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.ApplicationStatus;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> { // Vérifier si un bénévole a déjà postulé à une mission
    @Query("SELECT COUNT(a) > 0 FROM Application a WHERE a.volunteer = :volunteer AND a.mission = :mission AND a.status != :cancelledStatus")
    boolean existsByVolunteerAndMissionAndStatusNot(@Param("volunteer") Volunteer volunteer,
                                                    @Param("mission") Mission mission,
                                                    @Param("cancelledStatus") ApplicationStatus cancelledStatus);

    List<Application> findByVolunteerOrderByApplicationDateDesc(Volunteer volunteer);

    List<Application> findByMissionOrderByApplicationDateDesc(Mission mission);

    List<Application> findByMissionAndStatusOrderByApplicationDateDesc(Mission mission, ApplicationStatus status);

    @Query("SELECT a FROM Application a WHERE a.mission.organization.userId = :organizationId ORDER BY a.applicationDate DESC")
    List<Application> findByOrganizationIdOrderByApplicationDateDesc(@Param("organizationId") Long organizationId);

    @Query("SELECT a FROM Application a WHERE a.mission.organization.userId = :organizationId AND a.status = :status ORDER BY a.applicationDate DESC")
    List<Application> findByOrganizationIdAndStatusOrderByApplicationDateDesc(@Param("organizationId") Long organizationId,
                                                                              @Param("status") ApplicationStatus status);

    /// Vérifier les conflits de disponibilité pour un bénévole

    @Query("SELECT a FROM Application a WHERE a.volunteer = :volunteer AND a.status = 'ACCEPTED' " +
            "AND a.mission.period.startDate <= :endDate AND a.mission.period.endDate >= :startDate")
    List<Application> findConflictingAcceptedApplications(@Param("volunteer") Volunteer volunteer,
                                                          @Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate);

    Optional<Application> findByVolunteerAndMission(Volunteer volunteer, Mission mission);
}