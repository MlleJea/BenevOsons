package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Volunteer extends User{

    /// Attributs
    private String lastName;
    private String firstName;
    private LocalDate birthdate;

    @ManyToMany
    @JoinTable(name = "volunteer_skills",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "id_Skill")})
    private List<Skill> volunteerSkillsList;
    @ManyToMany
    @JoinTable(name = "volunteer_missions",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")})
    private List<Mission> volunteerMissionList;


    /// Constructor
    public Volunteer() {
    }



    /// Getters
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public List<Skill> getVolunteerSkillsList() {
        return volunteerSkillsList;
    }

    public List<Mission> getVolunteerMissionList() {
        return volunteerMissionList;
    }

    /// Setters
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setVolunteerSkillsList(List<Skill> volunteerSkillsList) {
        this.volunteerSkillsList = volunteerSkillsList;
    }

    public void setVolunteerMissionList(List<Mission> volunteerMissionList) {
        this.volunteerMissionList = volunteerMissionList;
    }
}
