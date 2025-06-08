package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;

import java.time.LocalDate;
import java.util.List;

public class RegistrationDto {

    /// Attributs
    private String email;
    private String password;
    private String confirmationPassword;
    private String phoneNumber;
    private RoleName roleName;
    private String name;
    private List<Address> addressList;

    ////// Volunteer
    private LocalDate birthDate;

    ////// Organization
    private String rna;

    /// Constructors
    public RegistrationDto() {
    }


    ////// Volunteer
    // Add
    public RegistrationDto(LocalDate birthDate, List<Address> addressList, String name, RoleName roleName,
                           String phoneNumber, String confirmationPassword, String password, String email) {
        this.birthDate = birthDate;
        this.addressList = addressList;
        this.name = name;
        this.roleName = roleName;
        this.phoneNumber = phoneNumber;
        this.confirmationPassword = confirmationPassword;
        this.password = password;
        this.email = email;
    }

    ////// Organization
    // Add
    public RegistrationDto(String email, String password,
                           String phoneNumber, RoleName roleName, String name, List<Address> addressList, String rna) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.name = name;
        this.addressList = addressList;
        this.rna = rna;
    }

    // Update

    public RegistrationDto(String password, String phoneNumber, String confirmationPassword, String name,
                           List<Address> addressList) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.confirmationPassword = confirmationPassword;
        this.name = name;
        this.addressList = addressList;
    }

    /// Getters

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public String getRna() {
        return rna;
    }

    /// Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public void setRna(String rna) {
        this.rna = rna;
    }
}
