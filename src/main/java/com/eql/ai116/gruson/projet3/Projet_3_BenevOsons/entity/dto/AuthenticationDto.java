package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

public class AuthenticationDto {

    /// Attributs
    private String email;
    private String password;

    /// Constructors
    public AuthenticationDto() {
    }

    public AuthenticationDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /// Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    /// Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
