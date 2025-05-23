package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Volunteer extends User{

    /// Attributs
    private LocalDate birthdate;

    @ManyToMany(mappedBy = "skillsVolunteerList")
    @JsonIgnore
    private List<Skill> volunteerSkillsList;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "volunteer_missions",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")})
    private List<Mission> volunteerMissionList;


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


    public List<Mission> getVolunteerMissionList() {
        return volunteerMissionList;
    }


    /// Setters


    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setVolunteerSkillsList(List<Skill> volunteerSkillsList) {
        this.volunteerSkillsList = volunteerSkillsList;
    }

    public void setVolunteerMissionList(List<Mission> volunteerMissionList) {
        this.volunteerMissionList = volunteerMissionList;
    }
}
