package com.ctrlaltdelinquents.backend.dto; // Or your appropriate DTO package

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor // Default constructor
@AllArgsConstructor // Constructor with all fields
public class SessionParticipantDTO {
    private String userId;
    private String username;
    // Optional: Add profilePicture field if you want to send it
    // private String profilePicture;
}