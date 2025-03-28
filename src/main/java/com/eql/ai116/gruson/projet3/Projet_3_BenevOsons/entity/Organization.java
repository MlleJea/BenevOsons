package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

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
public class Organization extends User{

    /// Attributs
    private String organization_name;

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

    /// Getters
    public String getOrganization_name() {
        return organization_name;
    }
    /// Setters
    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }
}
