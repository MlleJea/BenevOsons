package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;

import java.time.LocalDate;
import java.util.List;

public class UserWithAddressDto {

    private String email;
    private String password;
    private LocalDate registrationDate;
    private LocalDate unRegistrationDate;
    private String phoneNumber;
    private RoleName roleName;
    private String name;
    private List<Address> address;


    private String firstName;
    private LocalDate birthDate;



}
