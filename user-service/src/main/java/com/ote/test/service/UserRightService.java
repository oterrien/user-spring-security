package com.ote.test.service;

import com.ote.test.model.Perimeter;
import com.ote.test.repository.IUserRightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRightService {

    @Autowired
    private IUserRightRepository userRightRepository;

    public boolean doesUserOwnPrivilegeForApplicationOnPerimeter(String user, String application, String perimeter, String privilege) {

        List<Perimeter> perimeters = userRightRepository.getPerimeters(user, application);
        return perimeters.stream().
                filter(p -> p.getCode().equals(perimeter)).
                anyMatch(p -> p.getPrivileges().contains(privilege));
    }

    public List<String> getRoles(String user, String application) {

        List<Perimeter> perimeters = userRightRepository.getPerimeters(user, application);
        return perimeters.stream().
                map(perimeter -> perimeter.getPrivileges().stream().map(privilege -> perimeter.getCode() + "_" + privilege).collect(Collectors.toList())).
                flatMap(p -> p.stream()).
                collect(Collectors.toList());
    }
}
