package com.ote.test.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Perimeter {

    private final String code;
    private List<String> privileges = new ArrayList<>();
}
