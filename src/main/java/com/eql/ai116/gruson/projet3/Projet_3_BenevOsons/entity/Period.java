package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Period {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id")
    private Long periodId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "period", cascade = CascadeType.ALL)
    private List<Mission> periodMissionsList;

    /// Constructeurs
    public Period() {
    }

    public Period(Long periodId, LocalDateTime startDate, LocalDateTime endDate, List<Mission> periodMissionsList) {
        this.periodId = periodId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodMissionsList = periodMissionsList;
    }

    /// Getters
    public Long getPeriodId() {
        return periodId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public List<Mission> getPeriodMissionsList() {
        return periodMissionsList;
    }

    /// Setters
    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setPeriodMissionsList(List<Mission> periodMissionsList) {
        this.periodMissionsList = periodMissionsList;
    }
}
