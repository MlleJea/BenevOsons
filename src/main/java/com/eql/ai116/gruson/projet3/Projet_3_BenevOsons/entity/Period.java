package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Period {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long period_id;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "period", cascade = CascadeType.ALL)
    private List<Mission> periodMissionsList;

    /// Constructors
    public Period() {
    }

    public Period(Long period_id, LocalDate startDate, LocalDate endDate, List<Mission> periodMissionsList) {
        this.period_id = period_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodMissionsList = periodMissionsList;
    }

    /// Getters
    public Long getPeriod_id() {
        return period_id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Mission> getPeriodMissionsList() {
        return periodMissionsList;
    }

    /// Setters
    public void setPeriod_id(Long period_id) {
        this.period_id = period_id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPeriodMissionsList(List<Mission> periodMissionsList) {
        this.periodMissionsList = periodMissionsList;
    }
}
