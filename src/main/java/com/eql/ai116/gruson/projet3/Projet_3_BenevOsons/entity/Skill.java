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


    /// Getters

    /// Setters
}
