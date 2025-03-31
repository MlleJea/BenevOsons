package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Notification {

    /// Attributs
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long notification_id;
    private String label;

    @ManyToMany(mappedBy = "userNotificationList",cascade = CascadeType.ALL)
    private List<User> notificationsUserList;

    @ManyToMany(mappedBy = "newsNotificationList",cascade = CascadeType.ALL)
    private List<News> notificationsNewsList;

    @ManyToMany
    @JoinTable(name = "notification_missions",
            joinColumns = {@JoinColumn(name = "notification_id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")})
    private List<Mission> notificationMissionList;

    /// Constructeurs
    public Notification() {
    }

    public Notification(Long notification_id, String label, List<User> notificationsUserList,
                        List<News> notificationsNewsList, List<Mission> notificationMissionList) {
        this.notification_id = notification_id;
        this.label = label;
        this.notificationsUserList = notificationsUserList;
        this.notificationsNewsList = notificationsNewsList;
        this.notificationMissionList = notificationMissionList;
    }

    /// Getters
    public Long getNotification_id() {
        return notification_id;
    }

    public String getLabel() {
        return label;
    }

    public List<User> getNotificationsUserList() {
        return notificationsUserList;
    }

    public List<News> getNotificationsNewsList() {
        return notificationsNewsList;
    }

    public List<Mission> getNotificationMissionList() {
        return notificationMissionList;
    }
    /// Setters
    public void setNotification_id(Long notification_id) {
        this.notification_id = notification_id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setNotificationsUserList(List<User> notificationsUserList) {
        this.notificationsUserList = notificationsUserList;
    }

    public void setNotificationsNewsList(List<News> notificationsNewsList) {
        this.notificationsNewsList = notificationsNewsList;
    }

    public void setNotificationMissionList(List<Mission> notificationMissionList) {
        this.notificationMissionList = notificationMissionList;
    }
}
