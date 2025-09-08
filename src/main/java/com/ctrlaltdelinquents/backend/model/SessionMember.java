package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "session_members")
@IdClass(SessionMember.SessionMemberId.class)
public class SessionMember {

    @Id
    @ManyToOne
    @JoinColumn(name = "sessionid", referencedColumnName = "sessionid")
    private Session session;

    @Id
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;

    // Composite key class
    public static class SessionMemberId implements Serializable {
        private Integer session;
        private String user;

        // Default constructor
        public SessionMemberId() {
        }

        public SessionMemberId(Integer session, String user) {
            this.session = session;
            this.user = user;
        }

        // equals() and hashCode() methods
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            SessionMemberId that = (SessionMemberId) o;
            return session.equals(that.session) && user.equals(that.user);
        }

    }
}