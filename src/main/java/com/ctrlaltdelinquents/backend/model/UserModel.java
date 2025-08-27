package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "\"user\"")  
public class UserModel {

    @Id
    @Column(name = "userid")  
    private int userid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_picture")  
    private String profile_picture;  

    //getters and setters
    public int getUserID() {
        return userid;
    }

    public void setUserID(int userid) {
        this.userid = userid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profile_picture;
    }

    public void setProfilePicture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
