package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Grade;

public class SkillDto {

    /// Attributs
    private String labelSkill;
    private Grade grade;
    private Long skillTypeId;
    private Long volunteerId;

    /// Constructor
    public SkillDto() {
    }

    /// Getters
    public String getLabelSkill() {
        return labelSkill;
    }

    public Grade getGrade() {
        return grade;
    }

    public Long getSkillTypeId() {
        return skillTypeId;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }
    /// Setters
    public void setLabelSkill(String labelSkill) {
        this.labelSkill = labelSkill;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setSkillTypeId(Long skillTypeId) {
        this.skillTypeId = skillTypeId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }
}
