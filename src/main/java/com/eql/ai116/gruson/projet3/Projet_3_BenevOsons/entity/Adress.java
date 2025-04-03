package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
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
    private Long adress_id;
    private int streetNumber;
    private String streetName;
    private String streetType;
    private String postalCode;
    private String city;
    private Double latitude;
    private Double longitude;

    @ManyToMany(mappedBy = "userAdressList",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> userList;

    @OneToMany(mappedBy = "adress", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Mission> adressMissionsList;

    /// Constructeur
    public Adress() {
    }

    public Adress(Long adress_id, int streetNumber, String streetName, String streetType, String postalCode,
                  String city, Double latitude, Double longitude, List<User> userList, List<Mission> adressMissionsList) {
        this.adress_id = adress_id;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.streetType = streetType;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userList = userList;
        this.adressMissionsList = adressMissionsList;
    }

    /// Getters
    public Long getAdress_id() {
        return adress_id;
    }

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

    public List<User> getUserList() {
        return userList;
    }

    public List<Mission> getAdressMissionsList() {
        return adressMissionsList;
    }

    /// Setters
    public void setAdress_id(Long adress_id) {
        this.adress_id = adress_id;
    }

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

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setAdressMissionsList(List<Mission> adressMissionsList) {
        this.adressMissionsList = adressMissionsList;
    }

    /// To string
    @Override
    public String toString() {
        return "Adress{" +
                "adress_id=" + adress_id +
                ", streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", streetType='" + streetType + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
