package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;

import java.time.LocalDate;

public class RegistrationDto {

    /// Attributs
    private String email;
    private String password;
    private LocalDate registrationDate;
    private LocalDate unRegistrationDate;
    private String phoneNumber;
    private RoleName roleName;
    private String name;
    private AdressDto adressDto;

    ////// Volunteer
    private String firstName;
    private LocalDate birthDate;

    ////// Organization

    /// Constructors
    public RegistrationDto() {
    }
    ////// Volunteer
    public RegistrationDto(String email, String password, LocalDate registrationDate, LocalDate unRegistrationDate,
                           String phoneNumber, RoleName roleName, String name, String firstName, LocalDate birthDate,
                           AdressDto adressDto) {
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.unRegistrationDate = unRegistrationDate;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.name = name;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.adressDto = adressDto;
    }

    ////// Organization
    public RegistrationDto(String email, String password, LocalDate registrationDate, LocalDate unRegistrationDate,
                           String phoneNumber, RoleName roleName, String name, AdressDto adressDto) {
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.unRegistrationDate = unRegistrationDate;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.name = name;
        this.adressDto = adressDto;
    }

    /// Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public LocalDate getUnRegistrationDate() {
        return unRegistrationDate;
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

    public AdressDto getAdressDto() {
        return adressDto;
    }

    /// Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setUnRegistrationDate(LocalDate unRegistrationDate) {
        this.unRegistrationDate = unRegistrationDate;
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

    public void setAdressDto(AdressDto adressDto) {
        this.adressDto = adressDto;
    }
}
