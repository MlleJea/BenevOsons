package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Notification;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("SELECT n FROM Notification n JOIN n.notificationsUserList u WHERE u.userId = :userId")
    List<Notification> findByUserId(@Param("userId") Long userId);

}
