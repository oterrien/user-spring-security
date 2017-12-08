package com.ote.user.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "T_USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;

    @Column(name ="LOGIN")
    private String login;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserRightEntity> userRights;
}
