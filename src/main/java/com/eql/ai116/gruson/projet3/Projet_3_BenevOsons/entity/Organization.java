package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Organization extends User {

    /// Attributs

    private String rna;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<News> organizationNewsList;

    @OneToMany(mappedBy = "organization" , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<Mission> organizationMissionList;



    /// Constructeurs
    public Organization() {
    }

    /// Getters

    public String getRna() {
        return rna;
    }

    public List<News> getOrganizationNewsList() {
        return organizationNewsList;
    }

    public List<Mission> getOrganizationMissionList() {
        return organizationMissionList;
    }

    /// Setters

    public void setRna(String rna) {
        this.rna = rna;
    }
    
    public void setOrganizationNewsList(List<News> organizationNewsList) {
        this.organizationNewsList = organizationNewsList;
    }

    public void setOrganizationMissionList(List<Mission> organizationMissionList) {
        this.organizationMissionList = organizationMissionList;
    }
}
