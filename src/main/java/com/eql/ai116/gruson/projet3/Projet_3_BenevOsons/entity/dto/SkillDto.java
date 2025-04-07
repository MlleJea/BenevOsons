package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Grade;

public class SkillDto {

    /// Attributs
    private Long SkillId;
    private String labelSkill;
    private Grade grade;
    private String skillTypeLabel;
    private Long volunteerId;

    /// Constructor
    public SkillDto() {
    }

    /// Getters

    public Long getSkillId() {
        return SkillId;
    }

    public String getLabelSkill() {
        return labelSkill;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getSkillTypeLabel() {
        return skillTypeLabel;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    /// Setters

    public void setSkillId(Long skillId) {
        SkillId = skillId;
    }

    public void setLabelSkill(String labelSkill) {
        this.labelSkill = labelSkill;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setSkillTypeLabel(String skillTypeLabel) {
        this.skillTypeLabel = skillTypeLabel;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }
}
