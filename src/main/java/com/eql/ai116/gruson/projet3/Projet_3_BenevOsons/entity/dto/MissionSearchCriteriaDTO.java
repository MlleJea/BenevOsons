package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MissionSearchCriteriaDTO {
    private String city;
    private List<Long> skillTypeIds;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Getters & Setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<Long> getSkillTypeIds() { return skillTypeIds; }
    public void setSkillTypeIds(List<Long> skillTypeIds) { this.skillTypeIds = skillTypeIds; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}

