package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Mission {

    // Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long missionId;
    private String title;
    private String description;
    private Integer numberVolunteerSearch;
    private LocalDate publicationDate;
    private LocalDate publicationClosingDate;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Application> applications;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "missions_skill_type",
            joinColumns = {@JoinColumn(name = "mission_id")},
            inverseJoinColumns = {@JoinColumn(name = "id_skill_type")})
    private List<SkillTypes> missionSkillsTypeList;

    @ManyToMany(mappedBy = "notificationMissionList")
    @JsonIgnore
    private List<Notification> missionNotificationsList;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER )
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Organization organization;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "period_id")
    private Period period;

    /// Constructors
    public Mission() {
    }

    /// Getters
    public Long getMissionId() {
        return missionId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNumberVolunteerSearch() {
        return numberVolunteerSearch;
    }
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public LocalDate getPublicationClosingDate() {
        return publicationClosingDate;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public List<SkillTypes> getMissionSkillsTypeList() {
        return missionSkillsTypeList;
    }

    public Organization getOrganization() {
        return organization;
    }

    public List<Notification> getMissionNotificationsList() {
        return missionNotificationsList;
    }

    public Address getAddress() {
        return address;
    }

    public Period getPeriod() {
        return period;
    }

    /// Setters
    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumberVolunteerSearch(Integer numberVolunteerSearch) {
        this.numberVolunteerSearch = numberVolunteerSearch;
    }
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPublicationClosingDate(LocalDate publicationClosingDate) {
        this.publicationClosingDate = publicationClosingDate;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void setMissionSkillsTypeList(List<SkillTypes> missionSkillsTypeList) {
        this.missionSkillsTypeList = missionSkillsTypeList;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setMissionNotificationsList(List<Notification> missionNotificationsList) {
        this.missionNotificationsList = missionNotificationsList;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}