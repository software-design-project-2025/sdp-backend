package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"user\"")  // Keep team's table name
public class User {
    
    @Id
    @Column(name = "userid")
    private String userid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;  // For traditional auth

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "degreeid")
    private String degreeid;

    @Column(name = "yearofstudy")
    private int yearofstudy;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(unique = true, name = "supabase_user_id")
    private String supabaseUserId;  // For Supabase auth
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public User() {}

    public User(String userid, String username, String password, String email, String role) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        //this.is_active = true;
        this.createdAt = LocalDateTime.now();
    }
    

    public User(String supabaseUserId, String email, String displayName) {
        this.supabaseUserId = supabaseUserId;
        this.email = email;
        this.username = displayName;
        //this.is_active = true;
        this.createdAt = LocalDateTime.now();
    }
    

    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    //public boolean isIs_active() { return is_active; }
    //public void setIs_active(boolean is_active) { this.is_active = is_active; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
    
    public String getSupabaseUserId() { return supabaseUserId; }
    public void setSupabaseUserId(String supabaseUserId) { this.supabaseUserId = supabaseUserId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}