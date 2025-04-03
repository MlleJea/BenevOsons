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
    private List<Adress> adress;

    ////// Volunteer
    private String firstName;
    private LocalDate birthDate;

    ////// Organization
    private Long rna;

    /// Constructors
    public RegistrationDto() {
    }
    ////// Volunteer
    // Add
    public RegistrationDto(String email, String password,
                           String phoneNumber, RoleName roleName, String name, String firstName, LocalDate birthDate,
                           List<Adress> adress) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.adress = adress;
    }
    // Update

    public RegistrationDto(String password, String phoneNumber, String name,List<Adress> adress, String firstName) {

        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.adress = adress;
        this.firstName = firstName;
    }

    ////// Organization
    // Add
    public RegistrationDto(String email, String password,
                           String phoneNumber, RoleName roleName, String name, List<Adress> adress, Long rna) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.name = name;
        this.adress = adress;
        this.rna = rna;
    }

    // Update

    public RegistrationDto( String password, String phoneNumber, String name, List<Adress> adress,Long rna) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.adress = adress;
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

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Adress> getAdress() {
        return adress;
    }

    public Long getRna() {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAdress(List<Adress> adress) {
        this.adress = adress;
    }

    public void setRna(Long rna) {
        this.rna = rna;
    }
}
