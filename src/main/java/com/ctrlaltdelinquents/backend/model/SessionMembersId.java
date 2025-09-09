package com.ctrlaltdelinquents.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class SessionMembersId implements Serializable {

    private int sessionid;
    private String userid;

    public SessionMembersId() {}

    public SessionMembersId(int sessionid, String userid) {
        this.sessionid = sessionid;
        this.userid = userid;
    }
}
