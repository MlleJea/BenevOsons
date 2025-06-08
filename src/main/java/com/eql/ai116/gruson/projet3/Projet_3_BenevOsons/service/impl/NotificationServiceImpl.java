package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Application;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Notification;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.NotificationRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;


    public Notification createApplicationNotification(Application application) {
        String label = String.format("Nouvelle postulation de %s %s pour la mission '%s'",
                application.getVolunteer().getName(),
                application.getMission().getTitle());

        Notification notification = new Notification();
        notification.setLabel(label);

        // Associer l'organisation concernée
        List<User> userList = new ArrayList<>();
        userList.add(application.getMission().getOrganization());
        notification.setNotificationsUserList(userList);

        // Associer la mission concernée
        List<Mission> missionList = new ArrayList<>();
        missionList.add(application.getMission());
        notification.setNotificationMissionList(missionList);

        return notificationRepository.save(notification);
    }


    public Notification createApplicationResponseNotification(Application application, boolean accepted) {
        String status = accepted ? "acceptée" : "refusée";
        String label = String.format("Votre postulation pour la mission '%s' a été %s",
                application.getMission().getTitle(), status);

        Notification notification = new Notification();
        notification.setLabel(label);

        // Associer le bénévole concerné
        List<User> userList = new ArrayList<>();
        userList.add(application.getVolunteer());
        notification.setNotificationsUserList(userList);

        // Associer la mission concernée
        List<Mission> missionList = new ArrayList<>();
        missionList.add(application.getMission());
        notification.setNotificationMissionList(missionList);

        return notificationRepository.save(notification);
    }


    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
