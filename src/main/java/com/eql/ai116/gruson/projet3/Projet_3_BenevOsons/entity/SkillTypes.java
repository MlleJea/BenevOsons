package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    @Column(name = "id_skill_type")
    private Long idSkillType;
    private String label;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "skill_type_missions",
            joinColumns = {@JoinColumn(name = "id_skill_type")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")})
    private List<Mission> skillTypeMissionList;


    @OneToMany(mappedBy = "skillType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Skill> skillList;


    /// Constructeurs
    public SkillTypes() {
    }

    public SkillTypes(Long idSkillType, String label, List<Mission> skillTypeMissionList, List<Skill> skillList) {
        this.idSkillType = idSkillType;
        this.label = label;
        this.skillTypeMissionList = skillTypeMissionList;
        this.skillList = skillList;
    }

    /// Getters
    public Long getIdSkillType() {
        return idSkillType;
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
    public void setIdSkillType(Long idSkillType) {
        this.idSkillType = idSkillType;
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
