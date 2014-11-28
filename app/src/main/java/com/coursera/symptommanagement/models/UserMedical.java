package com.coursera.symptommanagement.models;


import java.io.Serializable;

public class UserMedical implements Serializable{

    private Long id;

    private String username;

    private String password;

    private String role;				// This may become a Role object

    private Long roleId;				// This will map to a doctorId or patientId


    public UserMedical() {}

    public UserMedical(String username, String password, String role, Long roleId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
