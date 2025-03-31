package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Role {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    @Enumerated(EnumType.STRING)
    RoleName roleName ;

    @ManyToMany(mappedBy = "role")  // Mapping inversé
    private List<User> users;

    /// Constructeurs

    public Role() { }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    /// Getters
    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName.toString();
    }
    /// Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
