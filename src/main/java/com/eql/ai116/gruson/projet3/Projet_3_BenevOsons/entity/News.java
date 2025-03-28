package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.List;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_news;
    private LocalDate addingDate;
    private LocalDate withdrawalDate;
    private String title;
    private String description;

    @ManyToMany
    @JoinTable(name = "news_notifications",
            joinColumns = {@JoinColumn(name = "id_news")},
            inverseJoinColumns = {@JoinColumn(name = "notification_id")})
    private List<Notification> newsNotificationList;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id")
    private Organization organization;

    /// Constructeurs
    public News() {
    }


    /// Getters

    /// Setters

}
