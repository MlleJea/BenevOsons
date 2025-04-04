package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;

import java.time.LocalDate;
import java.util.List;

public class RegistrationDto {

    /// Attributs
    private String email;
    private String password;
    private String phoneNumber;
    private RoleName roleName;
    private String name;
    private List<Adress> adressList;

    ////// Volunteer
    private LocalDate birthDate;

    ////// Organization
    private String rna;

    /// Constructors
    public RegistrationDto() {
    }
    ////// Volunteer
    // Add
    public RegistrationDto(String email, String password,
                           String phoneNumber, RoleName roleName, String name, LocalDate birthDate,
                           List<Adress> adressList) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.name = name;
        this.birthDate = birthDate;
        this.adressList = adressList;
    }
    // Update

    public RegistrationDto(String password, String phoneNumber, String name,List<Adress> adressList) {

        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.adressList = adressList;
    }

    ////// Organization
    // Add
    public RegistrationDto(String email, String password,
                           String phoneNumber, RoleName roleName, String name, List<Adress> adressList, String rna) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.name = name;
        this.adressList = adressList;
        this.rna = rna;
    }

    // Update

    public RegistrationDto( String password, String phoneNumber, String name, List<Adress> adressList,String rna) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.adressList = adressList;
        this.rna = rna;
    }

    /// Getters

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public List<Adress> getAdressList() {
        return adressList;
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

    public void setAdressList(List<Adress> adressList) {
        this.adressList = adressList;
    }

    public void setRna(String rna) {
        this.rna = rna;
    }
}
