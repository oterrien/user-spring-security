package com.ote.test;

import com.ote.test.model.Perimeter;
import com.ote.test.model.UserRight;
import com.ote.test.repository.IUserRepository;
import com.ote.test.repository.IUserRightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class UserServiceRunner {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(UserServiceRunner.class, args);
    }

    @Bean
    public IUserRepository userRepository() {

        List<UserDetails> userDetails = new ArrayList<>();
        userDetails.add(new User("steve.jobs", "password", new ArrayList<>()));
        userDetails.add(new User("bill.gates", "password", new ArrayList<>()));

        return (usr) -> userDetails.stream().
                filter(p -> p.getUsername().equals(usr)).findAny().
                orElse(null);
    }

    @Bean
    public IUserRightRepository userRightRepository() {

        List<UserRight> userRights = new ArrayList<>();

        //region steve.jobs for APPLE
        {
            UserRight userRight = new UserRight("steve.jobs", "APPLE");
            {
                Perimeter perimeter = new Perimeter("DEAL");
                perimeter.getPrivileges().add("READ");
                perimeter.getPrivileges().add("WRITE");
                userRight.getPerimeters().add(perimeter);
            }
            {
                Perimeter perimeter = new Perimeter("PAYMENT");
                perimeter.getPrivileges().add("AUTHORIZED");
                userRight.getPerimeters().add(perimeter);
            }
            userRights.add(userRight);
        }
        //endregion

        //region steve.jobs for PIXAR
        {
            UserRight userRight = new UserRight("steve.jobs", "PIXAR");
            {
                Perimeter perimeter = new Perimeter("DEAL");
                perimeter.getPrivileges().add("READ");
                perimeter.getPrivileges().add("WRITE");
                userRight.getPerimeters().add(perimeter);
            }
            {
                Perimeter perimeter = new Perimeter("PAYMENT");
                perimeter.getPrivileges().add("AUTHORIZED");
                userRight.getPerimeters().add(perimeter);
            }
            userRights.add(userRight);
        }
        //endregion

        //region bill.gates for MICROSOFT
        {
            UserRight userRight = new UserRight("bill.gates", "MICROSOFT");
            {
                Perimeter perimeter = new Perimeter("DEAL");
                perimeter.getPrivileges().add("READ");
                perimeter.getPrivileges().add("WRITE");
                userRight.getPerimeters().add(perimeter);
            }
            {
                Perimeter perimeter = new Perimeter("PAYMENT");
                perimeter.getPrivileges().add("AUTHORIZED");
                userRight.getPerimeters().add(perimeter);
            }
            userRights.add(userRight);
        }
        //endregion

        return (usr, app) ->
                userRights.stream().
                        filter(p -> p.getUser().equals(usr)).
                        filter(p -> p.getApplication().equals(app)).
                        flatMap(p -> p.getPerimeters().stream()).collect(Collectors.toList());

    }
}