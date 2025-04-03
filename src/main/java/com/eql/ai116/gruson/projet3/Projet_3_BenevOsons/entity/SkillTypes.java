package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

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

import java.util.List;

@Entity
public class SkillTypes {

    /// Attributs
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id_SkillType;
    private String label;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "skillType_missions",
            joinColumns = {@JoinColumn(name = "id_SkillType")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")})
    private List<Mission> skillTypeMissionList;


    @OneToMany(mappedBy = "skillType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Skill> skillList;


    /// Constructeurs
    public SkillTypes() {
    }

    public SkillTypes(Long id_SkillType, String label, List<Mission> skillTypeMissionList, List<Skill> skillList) {
        this.id_SkillType = id_SkillType;
        this.label = label;
        this.skillTypeMissionList = skillTypeMissionList;
        this.skillList = skillList;
    }

    /// Getters
    public Long getId_SkillType() {
        return id_SkillType;
    }

    public String getLabel() {
        return label;
    }

    public List<Mission> getSkillTypeMissionList() {
        return skillTypeMissionList;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    /// Setters
    public void setId_SkillType(Long id_SkillType) {
        this.id_SkillType = id_SkillType;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSkillTypeMissionList(List<Mission> skillTypeMissionList) {
        this.skillTypeMissionList = skillTypeMissionList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }
}
