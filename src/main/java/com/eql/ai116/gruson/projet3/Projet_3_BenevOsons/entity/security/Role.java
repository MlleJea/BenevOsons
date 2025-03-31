package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Role {

    /// Attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long role_id ;
    @Enumerated(EnumType.STRING)
    RoleName roleName ;


    /// Constructeurs

    public Role() { }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    /// Getters
    public Long getId() {
        return role_id;
    }

    public String getRoleName() {
        return roleName.toString();
    }
    /// Setters
    public void setId(Long role_id) {
        this.role_id = role_id;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
