package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

public enum ApplicationStatus {
    PENDING("En attente"),
    ACCEPTED("Acceptée"),
    REJECTED("Refusée"),
    CANCELLED("Annulée");

    private final String displayName;

    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
