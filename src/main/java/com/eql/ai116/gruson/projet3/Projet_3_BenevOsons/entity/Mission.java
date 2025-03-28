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

}
