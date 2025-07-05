package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.List;

/**
 * A user of the application, either a volunteer or an organization
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String password;
    private LocalDate registrationDate;
    private LocalDate unRegistrationDate;
    private String name;
    private String phoneNumber;

    // Link to other tables
    @ManyToMany
    @JoinTable(name = "user_address",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "address_id")})
    private List<Address> userAddressList;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "user_notifications",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "notification_id")})
    private List<Notification> userNotificationList;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "role_id")
    private Role role;

    /// Constructors
    public User() {
    }

    public User(Long userId, String email, String password, LocalDate registrationDate, String name, Role role,
                List<Address> userAddressList, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.name = name;
        this.role = role;
        this.userAddressList = userAddressList;
        this.phoneNumber = phoneNumber;
    }

    public User(Long userId, String email, String password, LocalDate registrationDate, LocalDate unRegistrationDate,
                String name, String phoneNumber, List<Address> userAddressList, List<Notification> userNotificationList,
                Role role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.unRegistrationDate = unRegistrationDate;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userAddressList = userAddressList;
        this.userNotificationList = userNotificationList;
        this.role = role;
    }

    /// Getters
    public Long getUserId() {
        return userId;
    }

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

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Address> getUserAddressList() {
        return userAddressList;
    }

    public List<Notification> getUserNotificationList() {
        return userNotificationList;
    }

    public Role getRole() {
        return role;
    }

    /// Setters
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserAddressList(List<Address> userAddressList) {
        this.userAddressList = userAddressList;
    }

    public void setUserNotificationList(List<Notification> userNotificationList) {
        this.userNotificationList = userNotificationList;
    }

    public void setRole(Role roles) {
        this.role = roles;
    }
    /// Méthodes


}
