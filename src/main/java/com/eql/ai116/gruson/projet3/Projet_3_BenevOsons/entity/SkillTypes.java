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

import java.util.List;

@Entity
public class SkillTypes {

    /// Attributs
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id_SkillType;
    private String label;

    @ManyToMany
    @JoinTable(name = "skillType_missions",
            joinColumns = {@JoinColumn(name = "id_SkillType")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")})
    private List<Mission> skillTypeMissionList;


    @OneToMany(mappedBy = "skillType", cascade = CascadeType.ALL)
    private List<Skill> skillList;


    /// Constructeurs
    public SkillTypes() {
    }

    public SkillTypes(String label, Long id_SkillType) {
        this.label = label;
        this.id_SkillType = id_SkillType;
    }

    /// Getters
    public Long getIdSkillType() {
        return id_SkillType;
    }

    public String getLabel() {
        return label;
    }

    /// Setters
    public void setLabel(String label) {
        this.label = label;
    }

    public void setIdSkillType(Long id_SkillType) {
        this.id_SkillType = id_SkillType;
    }
}
