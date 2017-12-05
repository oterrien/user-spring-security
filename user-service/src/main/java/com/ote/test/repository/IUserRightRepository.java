package com.ote.test.repository;

import com.ote.test.model.Perimeter;

import java.util.List;

public interface IUserRightRepository {

    List<Perimeter> getPerimeters(String user, String application);
}
