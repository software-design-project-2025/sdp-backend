package com.ctrlaltdelinquents.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GroupJoinRequestWithDetails {
    private Integer requestId;
    private Integer groupId;
    private String groupTitle;
    private String userId;
    // We'll use the user's bio as their display name for now
    private String userName;
    private String status;
}
