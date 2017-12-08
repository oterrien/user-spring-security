package com.ote.user.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "T_PERIMETER")
public class PerimeterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "CODE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    @JsonIgnore
    private PerimeterEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<PerimeterEntity> children;

    @ManyToMany
    @JoinTable(name = "T_PERIMETER_PRIVILEGE",
            joinColumns = @JoinColumn(name = "PERIMETER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID"))
    private List<PrivilegeEntity> privileges = new ArrayList<>();
}
