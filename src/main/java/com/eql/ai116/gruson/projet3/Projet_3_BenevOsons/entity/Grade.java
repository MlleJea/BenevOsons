package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

public enum Grade {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5");

    private final String number;

    /// Constructor
    Grade(String number) {
        this.number = number;
    }

    /// Getter
    public String getNumber() {
        return number;
    }
}
