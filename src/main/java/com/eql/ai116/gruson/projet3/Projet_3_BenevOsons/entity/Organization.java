package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Long rna;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<News> organizationNewsList;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "organization_missions",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")})
    private List<Mission> organizationMissionList;


    /// Constructeurs
    public Organization() {
    }

    /// Getters

    public Long getrna() {
        return rna;
    }

    public List<News> getOrganizationNewsList() {
        return organizationNewsList;
    }

    public List<Mission> getOrganizationMissionList() {
        return organizationMissionList;
    }

    /// Setters

    public void setrna(Long rna) {
        this.rna = rna;
    }
    
    public void setOrganizationNewsList(List<News> organizationNewsList) {
        this.organizationNewsList = organizationNewsList;
    }

    public void setOrganizationMissionList(List<Mission> organizationMissionList) {
        this.organizationMissionList = organizationMissionList;
    }
}
