package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Mission {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mission_id;
    private String description;
    private LocalDate publicationDate;
    private LocalDate publicationClosingDate;

    @ManyToMany(mappedBy = "volunteerMissionList",cascade = CascadeType.ALL)
    private List<Volunteer> missionVolunteerList;

    @ManyToMany(mappedBy = "skillTypeMissionList",cascade = CascadeType.ALL)
    private List<SkillTypes> missionSkillsTypeList;

    @ManyToMany(mappedBy = "organizationMissionList",cascade = CascadeType.ALL)
    private List<Organization> missionOrganizationList;

    @ManyToMany(mappedBy = "notificationMissionList",cascade = CascadeType.ALL)
    private List<Notification> missionNotificationsList;

    @ManyToOne
    @JoinColumn(referencedColumnName = "adress_id")
    private Adress adress;

    @ManyToOne
    @JoinColumn(referencedColumnName = "period_id")
    private Period period;

    /// Constructors
    public Mission() {
    }

    public Mission(Long mission_id, String description, LocalDate publicationDate, LocalDate publicationClosingDate,
                   List<Volunteer> missionVolunteerList, List<SkillTypes> missionSkillsTypeList,
                   List<Organization> missionOrganizationList, List<Notification> missionNotificationsList,
                   Adress adress, Period period) {
        this.mission_id = mission_id;
        this.description = description;
        this.publicationDate = publicationDate;
        this.publicationClosingDate = publicationClosingDate;
        this.missionVolunteerList = missionVolunteerList;
        this.missionSkillsTypeList = missionSkillsTypeList;
        this.missionOrganizationList = missionOrganizationList;
        this.missionNotificationsList = missionNotificationsList;
        this.adress = adress;
        this.period = period;
    }

    /// Getters
    public Period getPeriod() {
        return period;
    }

    public Adress getAdress() {
        return adress;
    }

    public List<Notification> getMissionNotificationsList() {
        return missionNotificationsList;
    }

    public List<Organization> getMissionOrganizationList() {
        return missionOrganizationList;
    }

    public List<SkillTypes> getMissionSkillsTypeList() {
        return missionSkillsTypeList;
    }

    public List<Volunteer> getMissionVolunteerList() {
        return missionVolunteerList;
    }

    public LocalDate getPublicationClosingDate() {
        return publicationClosingDate;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public Long getMission_id() {
        return mission_id;
    }
    /// Setters
    public void setMission_id(Long mission_id) {
        this.mission_id = mission_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPublicationClosingDate(LocalDate publicationClosingDate) {
        this.publicationClosingDate = publicationClosingDate;
    }

    public void setMissionVolunteerList(List<Volunteer> missionVolunteerList) {
        this.missionVolunteerList = missionVolunteerList;
    }

    public void setMissionSkillsTypeList(List<SkillTypes> missionSkillsTypeList) {
        this.missionSkillsTypeList = missionSkillsTypeList;
    }

    public void setMissionOrganizationList(List<Organization> missionOrganizationList) {
        this.missionOrganizationList = missionOrganizationList;
    }

    public void setMissionNotificationsList(List<Notification> missionNotificationsList) {
        this.missionNotificationsList = missionNotificationsList;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
