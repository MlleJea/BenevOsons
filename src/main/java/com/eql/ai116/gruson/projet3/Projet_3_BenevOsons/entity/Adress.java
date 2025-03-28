package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

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
    private Long latitude;
    private Long longitude;

    @ManyToMany(mappedBy = "userAdressList",cascade = CascadeType.ALL)
    private List<User> userList;

    @OneToMany(mappedBy = "adress", cascade = CascadeType.ALL)
    private List<Mission> adressMissionsList;

    /// Constructeur
    public Adress() {
    }

    public Adress(Long adress_id, int streetNumber, String streetName, String streetType, String postalCode,
                  String city, Long latitude, Long longitude) {
        adress_id = adress_id;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.streetType = streetType;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /// Getters
    public Long getId() {
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

    public Long getLatitude() {
        return latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    /// Setters
    public void setId(Long adress_id) {
        adress_id = adress_id;
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

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }
}
