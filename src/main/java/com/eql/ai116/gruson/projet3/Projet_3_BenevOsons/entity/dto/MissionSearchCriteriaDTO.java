package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MissionSearchCriteriaDTO {
    private String postalCode;
    private List<Long> skillTypeIds;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double userLatitude;
    private Double userLongitude;
    private Double radiusKm;


    // Getters & Setters


    public String getPostalCode() {
        return postalCode;
    }

    public Double getUserLatitude() {
        return userLatitude;
    }

    public Double getUserLongitude() {
        return userLongitude;
    }

    public Double getRadiusKm() {
        return radiusKm;
    }

    public List<Long> getSkillTypeIds() { return skillTypeIds; }
    public void setSkillTypeIds(List<Long> skillTypeIds) { this.skillTypeIds = skillTypeIds; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}


