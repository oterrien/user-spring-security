package com.ote.test.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRight {

    private final String user;
    private final String application;
    private final List<Perimeter> perimeters = new ArrayList<>();
}
