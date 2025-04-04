package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import jakarta.persistence.Column;
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
    @Column(name = "news_id")
    private Long idNews;
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

    public News(Long idNews, LocalDate addingDate, LocalDate withdrawalDate, String title, String description,
                List<Notification> newsNotificationList, Organization organization) {
        this.idNews = idNews;
        this.addingDate = addingDate;
        this.withdrawalDate = withdrawalDate;
        this.title = title;
        this.description = description;
        this.newsNotificationList = newsNotificationList;
        this.organization = organization;
    }
    /// Getters
    public Long getIdNews() {
        return idNews;
    }

    public LocalDate getAddingDate() {
        return addingDate;
    }

    public LocalDate getWithdrawalDate() {
        return withdrawalDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Notification> getNewsNotificationList() {
        return newsNotificationList;
    }

    public Organization getOrganization() {
        return organization;
    }
    
    /// Setters
    public void setIdNews(Long idNews) {
        this.idNews = idNews;
    }

    public void setAddingDate(LocalDate addingDate) {
        this.addingDate = addingDate;
    }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNewsNotificationList(List<Notification> newsNotificationList) {
        this.newsNotificationList = newsNotificationList;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
