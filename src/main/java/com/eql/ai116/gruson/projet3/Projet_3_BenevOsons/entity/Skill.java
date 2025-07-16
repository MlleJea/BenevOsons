package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Skill {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long idSkill;
    private String labelSkill;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id_skill_type")
    private SkillTypes skillType;

    @ManyToMany
    @JoinTable(name = "volunteer_skills",
            joinColumns = {@JoinColumn(name = "id_skill")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @JsonIgnore
    private List<Volunteer> skillsVolunteerList;

    /// Constructeurs
    public Skill() {
    }

    public Skill(List<Volunteer> skillsVolunteerList, SkillTypes skillType, Grade grade, String labelSkill, Long idSkill) {
        this.skillsVolunteerList = skillsVolunteerList;
        this.skillType = skillType;
        this.grade = grade;
        this.labelSkill = labelSkill;
        this.idSkill = idSkill;
    }


    /// Getters
    public Long getIdSkill() {
        return idSkill;
    }

    public String getLabelSkill() {
        return labelSkill;
    }

    public Grade getGrade() {
        return grade;
    }

    public SkillTypes getSkillType() {
        return skillType;
    }

    public List<Volunteer> getSkillsVolunteerList() {
        return skillsVolunteerList;
    }

    /// Setters
    public void setIdSkill(Long idSkill) {
        this.idSkill = idSkill;
    }

    public void setLabelSkill(String labelSkill) {
        this.labelSkill = labelSkill;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setSkillType(SkillTypes skillType) {
        this.skillType = skillType;
    }

    public void setSkillsVolunteerList(List<Volunteer> skillsVolunteerList) {
        this.skillsVolunteerList = skillsVolunteerList;
    }
}
