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


    /// Getters


    /// Setters

}
