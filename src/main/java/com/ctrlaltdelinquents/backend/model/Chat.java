package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @Column(name = "chatid")
    private int chatid;

    @ManyToOne
    @JoinColumn(name = "user1id")  // matches DB column
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2id")  // matches DB column
    private User user2;
}
