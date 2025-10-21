package com.ctrlaltdelinquents.backend.model;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class SessionMembersId implements Serializable {

    private Integer sessionid; // Changed to Integer to match Session.sessionId
    private String userid;

    public SessionMembersId() {}

    public SessionMembersId(Integer sessionid, String userid) {
        this.sessionid = sessionid;
        this.userid = userid;
    }

    // You MUST override equals() and hashCode() for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionMembersId that = (SessionMembersId) o;
        return Objects.equals(sessionid, that.sessionid) &&
                Objects.equals(userid, that.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionid, userid);
    }
}