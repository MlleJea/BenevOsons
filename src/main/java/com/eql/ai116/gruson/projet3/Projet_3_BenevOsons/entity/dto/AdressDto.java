package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

public class AdressDto {

    /// Attributs
    private int streetNumber;
    private String streetName;
    private String streetType;
    private String postalCode;
    private String city;
    private Double latitude;
    private Double longitude;

    /// Constructors
    public AdressDto() {
    }

    public AdressDto(int streetNumber, String streetName, String streetType, String postalCode, String city, Double latitude, Double longitude) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.streetType = streetType;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /// Getters
    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetType() {
        return streetType;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    /// Setters
    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
