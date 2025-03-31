package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Organization extends User {

    /// Attributs
    ///
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<News> organizationNewsList;

    @ManyToMany
    @JoinTable(name = "organization_missions",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")})
    private List<Mission> organizationMissionList;


    /// Constructeurs
    public Organization() {
    }

    public Organization(Long user_id, String email, String password, LocalDate registrationDate,
                        LocalDate unRegistrationDate, String name, String phoneNumber, List<Adress> userAdressList,
                        List<Notification> userNotificationList, List<Role> role, List<News> organizationNewsList,
                        List<Mission> organizationMissionList) {
        super(user_id, email, password, registrationDate, unRegistrationDate, name, phoneNumber, userAdressList,
                userNotificationList, role);
        this.organizationNewsList = organizationNewsList;
        this.organizationMissionList = organizationMissionList;
    }

    /// Getters

    public List<News> getOrganizationNewsList() {
        return organizationNewsList;
    }

    public List<Mission> getOrganizationMissionList() {
        return organizationMissionList;
    }

    /// Setters

    public void setOrganizationNewsList(List<News> organizationNewsList) {
        this.organizationNewsList = organizationNewsList;
    }

    public void setOrganizationMissionList(List<Mission> organizationMissionList) {
        this.organizationMissionList = organizationMissionList;
    }
}
