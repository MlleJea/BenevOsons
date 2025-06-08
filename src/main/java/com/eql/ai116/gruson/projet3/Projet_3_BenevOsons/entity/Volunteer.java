package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Volunteer extends User {

    /// Attributs
    private LocalDate birthdate;

    @ManyToMany(mappedBy = "skillsVolunteerList")
    @JsonIgnore
    private List<Skill> volunteerSkillsList;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Application> applications;


    /// Constructor
    public Volunteer() {
    }


    /// Getters


    public LocalDate getBirthdate() {
        return birthdate;
    }

    public List<Skill> getVolunteerSkillsList() {
        return volunteerSkillsList;
    }

    public List<Application> getApplications() {
        return applications;
    }


    /// Setters

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setVolunteerSkillsList(List<Skill> volunteerSkillsList) {
        this.volunteerSkillsList = volunteerSkillsList;
    }

}
