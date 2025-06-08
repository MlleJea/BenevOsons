package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Application;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NotificationService {
    Notification createApplicationNotification(Application application);
    Notification createApplicationResponseNotification(Application application, boolean accepted);
    List<Notification> getUserNotifications(Long userId);
}
