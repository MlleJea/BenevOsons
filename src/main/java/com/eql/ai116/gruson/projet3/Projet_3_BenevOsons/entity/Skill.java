package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Skill {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Skill;
    private Long labelSkill;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id_SkillType")
    private SkillTypes skillType;

    @ManyToMany(mappedBy = "volunteerSkillsList",cascade = CascadeType.ALL)
    private List<Volunteer> skillsVolunteerList;

    /// Constructeurs
    public Skill() {
    }

    public Skill(List<Volunteer> skillsVolunteerList, SkillTypes skillType, Grade grade, Long labelSkill, Long id_Skill) {
        this.skillsVolunteerList = skillsVolunteerList;
        this.skillType = skillType;
        this.grade = grade;
        this.labelSkill = labelSkill;
        this.id_Skill = id_Skill;
    }

    /// Getters
    public Long getId_Skill() {
        return id_Skill;
    }

    public Long getLabelSkill() {
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
    public void setId_Skill(Long id_Skill) {
        this.id_Skill = id_Skill;
    }

    public void setLabelSkill(Long labelSkill) {
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
