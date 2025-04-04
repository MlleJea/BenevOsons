package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Adress {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adress_id")
    private Long adressId;
    private int streetNumber;
    private String streetName;
    private String postalCode;
    private String city;
    private Double latitude;
    private Double longitude;

    @ManyToMany(mappedBy = "userAdressList",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> adressUserList;

    @OneToMany(mappedBy = "adress", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Mission> adressMissionsList;



    /// Constructeur
    public Adress() {
    }

    public Adress(Long adressId, int streetNumber, String streetName, String postalCode,
                  String city, Double latitude, Double longitude, List<User> adressUserList, List<Mission> adressMissionsList) {
        this.adressId = adressId;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adressUserList = adressUserList;
        this.adressMissionsList = adressMissionsList;
    }

    /// Getters
    public Long getAdressId() {
        return adressId;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
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

    public List<User> getAdressUserList() {
        return adressUserList;
    }

    public List<Mission> getAdressMissionsList() {
        return adressMissionsList;
    }

    /// Setters
    public void setAdressId(Long adressId) {
        this.adressId = adressId;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
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

    public void setAdressUserList(List<User> adressUserList) {
        this.adressUserList = adressUserList;
    }

    public void setAdressMissionsList(List<Mission> adressMissionsList) {
        this.adressMissionsList = adressMissionsList;
    }

    /// To string
    @Override
    public String toString() {
        return "Adress{" +
                "adress_id=" + adressId +
                ", streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
