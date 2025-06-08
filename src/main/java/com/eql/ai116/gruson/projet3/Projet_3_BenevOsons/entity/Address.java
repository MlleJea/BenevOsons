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
public class Address {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String city;
    private Double latitude;
    private Double longitude;

    @ManyToMany(mappedBy = "userAddressList",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> addressUserList;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Mission> addressMissionsList;



    /// Constructeur
    public Address() {
    }

    public Address(Long addressId, String streetNumber, String streetName, String postalCode,
                  String city, Double latitude, Double longitude, List<User> addressUserList, List<Mission> addressMissionsList) {
        this.addressId = addressId;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressUserList = addressUserList;
        this.addressMissionsList = addressMissionsList;
    }

    /// Getters
    public Long getAddressId() {
        return addressId;
    }

    public String getStreetNumber() {
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

    public List<User> getAddressUserList() {
        return addressUserList;
    }

    public List<Mission> getAddressMissionsList() {
        return addressMissionsList;
    }

    /// Setters
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public void setStreetNumber(String streetNumber) {
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

    public void setAddressUserList(List<User> addressUserList) {
        this.addressUserList = addressUserList;
    }

    public void setAddressMissionsList(List<Mission> addressMissionsList) {
        this.addressMissionsList = addressMissionsList;
    }

    /// To string
    @Override
    public String toString() {
        return "Address{" +
                "address_id=" + addressId +
                ", streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
