package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @Column(name = "userid")
    private String userid;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "degreeid")
    private String degreeid;

    @Column(name = "yearofstudy")
    private int yearofstudy; // CORRECT: matches database INT type

    @Column(name = "bio")
    private String bio;

    @Column(name = "status")
    private String status;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(unique = true, name = "supabase_user_id")
    private String supabaseUserId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User() {
    }

    public User(String userid, String username, String email, String role) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    public User(String supabaseUserId, String email, String displayName) {
        this.supabaseUserId = supabaseUserId;
        this.email = email;
        this.username = displayName;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDegreeid() {
        return degreeid;
    }

    public void setDegreeid(String degreeid) {
        this.degreeid = degreeid;
    }

    public int getYearofstudy() {
        return yearofstudy;
    }

    public void setYearofstudy(int yearofstudy) {
        this.yearofstudy = yearofstudy;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSupabaseUserId() {
        return supabaseUserId;
    }

    public void setSupabaseUserId(String supabaseUserId) {
        this.supabaseUserId = supabaseUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}