package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security;

public class BearerToken {

    /// Attributs
    private final String accessToken ;
    private final String tokenType ;

    /// Constructor
    public BearerToken(String accessToken , String tokenType) {
        this.tokenType = tokenType ;
        this.accessToken = accessToken;
    }

    /// Getters
    public String getAccessToken() {
        return accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }

}
