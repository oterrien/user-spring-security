package com.ote.user.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "T_USER_RIGHT")
public class UserRightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID")
    private ApplicationEntity application;

    @ManyToMany
    @JoinTable(name = "T_USER_RIGHT_PERIMETER",
            joinColumns = @JoinColumn(name = "USER_RIGHT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PERIMETER_ID", referencedColumnName = "ID"))
    private List<PerimeterEntity> perimeters = new ArrayList<>();
}
