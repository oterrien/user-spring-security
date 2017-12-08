package com.ote.user.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "T_PRIVILEGE")
public class PrivilegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "CODE")
    private String code;
}
