package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto;

public class UserDto {

    private final Long id;
    private final String name;
    private final String token;

    /// Constructor
    public UserDto(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
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
}
