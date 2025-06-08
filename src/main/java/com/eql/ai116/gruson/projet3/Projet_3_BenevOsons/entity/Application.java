package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "application_id")
private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "response_date")
    private LocalDate responseDate;

    @Column(name = "response_message", columnDefinition = "TEXT")
    private String responseMessage;

    // Constructeurs
    public Application() {
    }


    public Application(Volunteer volunteer, Mission mission, String message, ApplicationStatus status, LocalDate applicationDate) {
        this.volunteer = volunteer;
        this.mission = mission;
        this.message = message;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    // Getters
    public Long getApplicationId() {
        return applicationId;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public Mission getMission() {
        return mission;
    }

    public String getMessage() {
        return message;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public LocalDate getResponseDate() {
        return responseDate;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    // Setters
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setResponseDate(LocalDate responseDate) {
        this.responseDate = responseDate;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    ///  Méthodes

    public void accept(String responseMessage) {
        this.status = ApplicationStatus.ACCEPTED;
        this.responseDate = LocalDate.now();
        this.responseMessage = responseMessage;
    }

    public void reject(String responseMessage) {
        this.status = ApplicationStatus.REJECTED;
        this.responseDate = LocalDate.now();
        this.responseMessage = responseMessage;
    }

    public void cancel() {
        if (this.status == ApplicationStatus.PENDING) {
            this.status = ApplicationStatus.CANCELLED;
            this.responseDate = LocalDate.now();
        }
    }

    public boolean canBeCancelled() {
        return this.status == ApplicationStatus.PENDING;
    }
}