package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission,Long> {

    List<Mission> findAllByOrganization_UserId(Long userId);

    @Query("SELECT DISTINCT m FROM Mission m JOIN Application a ON m.missionId = a.mission.missionId " +
            "WHERE a.volunteer.userId = :volunteerId")
    List<Mission> findAllMissionsByVolunteerUserId(@Param("volunteerId") Long volunteerId);
}
