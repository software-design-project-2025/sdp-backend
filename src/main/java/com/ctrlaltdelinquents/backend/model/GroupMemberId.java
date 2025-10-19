package com.ctrlaltdelinquents.backend.model;

import java.io.Serializable;
import java.util.Objects;

// This helper class is required for entities with a composite primary key.
public class GroupMemberId implements Serializable {
    private Integer groupid;
    private String userid;

    public GroupMemberId() {
    }

    public GroupMemberId(Integer groupid, String userid) {
        this.groupid = groupid;
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(groupid, that.groupid) && Objects.equals(userid, that.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupid, userid);
    }
}
