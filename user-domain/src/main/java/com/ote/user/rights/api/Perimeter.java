package com.ote.user.rights.api;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Perimeter {

    private final String code;
    private final List<Perimeter> children = new ArrayList<>();
    private final List<String> privileges = new ArrayList<>();
    //private final boolean isAll;

    public Perimeter(String code) {
        this.code = code;
       // isAll = code.endsWith("*");
    }
}
