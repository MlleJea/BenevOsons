package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

public class UserDto {

    private final Long id;
    private final String name;
    private final String token;
    private final String role;

    /// Constructor
    public UserDto(Long id, String name, String token, String role) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.role = role;
    }

    /// Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
