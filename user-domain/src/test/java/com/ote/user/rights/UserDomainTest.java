package com.ote.user.rights;

import com.ote.user.rights.api.IUserRightService;
import com.ote.user.rights.api.Perimeter;
import com.ote.user.rights.api.PerimeterPath;
import com.ote.user.rights.api.UserRightServiceProvider;
import com.ote.user.rights.api.exception.ApplicationNotFoundException;
import com.ote.user.rights.api.exception.RoleNotFoundException;
import com.ote.user.rights.api.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UserDomainTest {

    @Test
    @DisplayName("Test PerimeterPath Structure")
    public void testPerimeterPath() {

        SoftAssertions assertions = new SoftAssertions();

        PerimeterPath path = new PerimeterPath.Builder("Deal").build();
        assertions.assertThat(path.toString()).isEqualTo("Deal");

        path = new PerimeterPath.Builder("Deal").then("GLE").build();
        assertions.assertThat(path.toString()).isEqualTo("Deal/GLE");

        path = new PerimeterPath.Builder("Deal").then("GLE").then("Dash").build();
        assertions.assertThat(path.toString()).isEqualTo("Deal/GLE/Dash");

        assertions.assertAll();
    }

    @Test
    @DisplayName("Test UserRight Structure")
    public void testUserRightStructure() throws IOException {

        UserRight userRights = new UserRight("rene.barjavel", "SLA");
        Perimeter dealPerimeter = new Perimeter("Deal");
        Perimeter glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add("ReadWrite");
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add("ReadOnly");
        userRights.getPerimeters().add(dealPerimeter);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userRights).isNotNull();
        assertions.assertThat(userRights.getUser()).isEqualTo("rene.barjavel");
        assertions.assertThat(userRights.getApplication()).isEqualTo("SLA");
        assertions.assertThat(userRights.getPerimeters()).hasSize(1);
        assertions.assertThat(userRights.getPerimeters().get(0).getCode()).isEqualTo("Deal");
        assertions.assertThat(userRights.getPerimeters().get(0).getPrivileges()).hasSize(1);
        assertions.assertThat(userRights.getPerimeters().get(0).getPrivileges().get(0)).isEqualTo("ReadOnly");
        assertions.assertThat(userRights.getPerimeters().get(0).getChildren()).hasSize(1);
        assertions.assertThat(userRights.getPerimeters().get(0).getChildren().get(0).getCode()).isEqualTo("GLE");
        assertions.assertThat(userRights.getPerimeters().get(0).getChildren().get(0).getPrivileges()).hasSize(1);
        assertions.assertThat(userRights.getPerimeters().get(0).getChildren().get(0).getPrivileges().get(0)).isEqualTo("ReadWrite");
        assertions.assertAll();
    }

    @Test
    @DisplayName("Test user not found")
    public void testUserNotFoundException() {

        UserRightRepositoryMock userRightsRepositoryMock = new UserRightRepositoryMock();
        IUserRightService userRightService = UserRightServiceProvider.getInstance().getFactory().createService(userRightsRepositoryMock);

        UserRight userRights = new UserRight("rene.barjavel", "SLA");
        Perimeter dealPerimeter = new Perimeter("Deal");
        Perimeter glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add("ReadWrite");
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add("ReadOnly");
        userRights.getPerimeters().add(dealPerimeter);
        userRightsRepositoryMock.getUserRightList().add(userRights);

        Assertions.assertThatThrownBy(() -> userRightService.
                doesUserOwnPrivilegeForApplicationOnPerimeter("BAD", "SLA", new PerimeterPath.Builder("Deal").build(), "ReadWrite")).
                isInstanceOf(UserNotFoundException.class);

    }

    @Test
    @DisplayName("Test application not found")
    public void testApplicationNotFoundException() {

        UserRightRepositoryMock userRightsRepositoryMock = new UserRightRepositoryMock();
        IUserRightService userRightService = UserRightServiceProvider.getInstance().getFactory().createService(userRightsRepositoryMock);

        UserRight userRights = new UserRight("rene.barjavel", "SLA");
        Perimeter dealPerimeter = new Perimeter("Deal");
        Perimeter glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add("ReadWrite");
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add("ReadOnly");
        userRights.getPerimeters().add(dealPerimeter);
        userRightsRepositoryMock.getUserRightList().add(userRights);


        Assertions.assertThatThrownBy(() -> userRightService.
                doesUserOwnPrivilegeForApplicationOnPerimeter("rene.barjavel", "BAD", new PerimeterPath.Builder("Deal").build(), "ReadWrite")).
                isInstanceOf(ApplicationNotFoundException.class);
    }

    @Test
    @DisplayName("Test application not found for a given user (role not found)")
    public void testApplicationNotFoundExceptionForUser() {

        UserRightRepositoryMock userRightsRepositoryMock = new UserRightRepositoryMock();
        IUserRightService userRightService = UserRightServiceProvider.getInstance().getFactory().createService(userRightsRepositoryMock);

        UserRight userRights = new UserRight("rene.barjavel", "SLA");
        Perimeter dealPerimeter = new Perimeter("Deal");
        Perimeter glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add("ReadWrite");
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add("ReadOnly");
        userRights.getPerimeters().add(dealPerimeter);
        userRightsRepositoryMock.getUserRightList().add(userRights);

        userRights = new UserRight("bernard.werber", "RADAR");
        dealPerimeter = new Perimeter("Deal");
        glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add("ReadWrite");
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add("ReadOnly");
        userRights.getPerimeters().add(dealPerimeter);
        userRightsRepositoryMock.getUserRightList().add(userRights);

        Assertions.assertThatThrownBy(() -> userRightService.
                doesUserOwnPrivilegeForApplicationOnPerimeter("rene.barjavel", "RADAR", new PerimeterPath.Builder("Deal").build(), "ReadWrite")).
                isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    @DisplayName("Test perimeter path is not defined for a given user and application")
    public void testPerimeterPathIsNotDefinedForAnApplication() throws Exception {

        UserRightRepositoryMock userRightsRepositoryMock = new UserRightRepositoryMock();
        IUserRightService userRightService = UserRightServiceProvider.getInstance().getFactory().createService(userRightsRepositoryMock);

        UserRight userRights = new UserRight("rene.barjavel", "SLA");
        Perimeter dealPerimeter = new Perimeter("Deal");
        Perimeter glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add("ReadWrite");
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add("ReadOnly");
        userRights.getPerimeters().add(dealPerimeter);
        userRightsRepositoryMock.getUserRightList().add(userRights);

        boolean isGranted = userRightService.doesUserOwnPrivilegeForApplicationOnPerimeter("rene.barjavel", "SLA", new PerimeterPath.Builder("NONE").build(), "ReadWrite");
        Assertions.assertThat(isGranted).isFalse();
    }

    @Test
    public void testRoleIsNotDefinedForAUserAndAnApplication() throws Exception {

        UserRightRepositoryMock userRightsRepositoryMock = new UserRightRepositoryMock();
        IUserRightService userRightService = UserRightServiceProvider.getInstance().getFactory().createService(userRightsRepositoryMock);

        UserRight userRights = new UserRight("rene.barjavel", "SLA");
        Perimeter dealPerimeter = new Perimeter("Deal");
        Perimeter glePerimeter = new Perimeter("GLE");
        dealPerimeter.getChildren().add(glePerimeter);
        userRights.getPerimeters().add(dealPerimeter);
        userRightsRepositoryMock.getUserRightList().add(userRights);

        Assertions.assertThatThrownBy(() -> userRightService.
                doesUserOwnPrivilegeForApplicationOnPerimeter("rene.barjavel", "SLA", new PerimeterPath.Builder("NONE").then("NONE").build(), "ReadWrite")).
                isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    public void testPerimeterPathIsDefinedForAnApplication() throws Exception {

        UserRightRepositoryMock userRightsRepositoryMock = new UserRightRepositoryMock();
        IUserRightService userRightService = UserRightServiceProvider.getInstance().getFactory().createService(userRightsRepositoryMock);

        UserRight userRights = new UserRight("rene.barjavel", "SLA");
        Perimeter dealPerimeter = new Perimeter("Deal");
        Perimeter glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add("ReadWrite");
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add("ReadOnly");
        userRights.getPerimeters().add(dealPerimeter);
        userRightsRepositoryMock.getUserRightList().add(userRights);

        SoftAssertions assertions = new SoftAssertions();

        // Check with path Deal
        boolean isGranted = userRightService.doesUserOwnPrivilegeForApplicationOnPerimeter("rene.barjavel", "SLA", new PerimeterPath.Builder("Deal").build(), "ReadOnly");
        assertions.assertThat(isGranted).isTrue();

        // Check with path Deal/GLE
        /*isGranted = userRightService.doesUserOwnPrivilegeForApplicationOnPerimeter("rene.barjavel", "SLA", new PerimeterPath.Builder("Deal").then("GLE").build(), "ReadOnly");
        assertions.assertThat(isGranted).isTrue();*/
        assertions.assertAll();
    }

   /*@Test
    public void checkUserPrivilege() throws Exception {

        UserRight userRights = new UserRight(new User("rene.barjavel"), new Application("SLA"));
        Perimeter dealPerimeter = new Perimeter("Deal");
        Perimeter glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add(new Privilege("ReadWrite"));
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add(new Privilege("ReadOnly"));
        userRights.getPerimeters().add(dealPerimeter);

        UserRightRepositoryMock userRightsRepositoryMock = new UserRightRepositoryMock();
        userRightsRepositoryMock.getUserRightList().add(userRights);

        IUserRightService userRightsService = new UserRightsService(userRightsRepositoryMock);

        SoftAssertions assertions = new SoftAssertions();

        // Check with path Deal
        boolean hasPrivilege = userRightsService.hasPrivilege(new Privilege("ReadOnly"), new User("rene.barjavel"), new Application("SLA"),
                PerimeterPath.builder().startsWith("Deal").build());
        assertions.assertThat(hasPrivilege).as("check ReadOnly for Deal").isTrue();

        hasPrivilege = userRightsService.hasPrivilege(new Privilege("ReadWrite"), new User("rene.barjavel"), new Application("SLA"),
                PerimeterPath.builder().startsWith("Deal").build());
        assertions.assertThat(hasPrivilege).as("check ReadWrite for Deal").isFalse();

        // Check with path Deal/GLE
        hasPrivilege = userRightsService.hasPrivilege(new Privilege("ReadOnly"), new User("rene.barjavel"), new Application("SLA"),
                PerimeterPath.builder().startsWith("Deal").then("GLE").build());
        assertions.assertThat(hasPrivilege).as("check ReadOnly for Deal/GLE").isTrue();

        hasPrivilege = userRightsService.hasPrivilege(new Privilege("ReadWrite"), new User("rene.barjavel"), new Application("SLA"),
                PerimeterPath.builder().startsWith("Deal").then("GLE").build());
        assertions.assertThat(hasPrivilege).as("check ReadWrite for Deal").isTrue();

        assertions.assertAll();
    }


    @Test
    public void checkAllPerimeters() throws Exception {

        UserRight userRights = new UserRight(new User("rene.barjavel"), new Application("SLA"));
        Perimeter dealPerimeter = new Perimeter("ALL", true);
        Perimeter glePerimeter = new Perimeter("GLE");
        glePerimeter.getPrivileges().add(new Privilege("ReadWrite"));
        dealPerimeter.getChildren().add(glePerimeter);
        dealPerimeter.getPrivileges().add(new Privilege("ReadOnly"));
        userRights.getPerimeters().add(dealPerimeter);

        UserRightRepositoryMock userRightsRepositoryMock = new UserRightRepositoryMock();
        userRightsRepositoryMock.getUserRightList().add(userRights);

        IUserRightService userRightsService = new UserRightsService(userRightsRepositoryMock);

        SoftAssertions assertions = new SoftAssertions();

        // Check with path Deal
        boolean hasPrivilege = userRightsService.hasPrivilege(new Privilege("ReadOnly"), new User("rene.barjavel"), new Application("SLA"),
                PerimeterPath.builder().startsWith("Deal").build());
        assertions.assertThat(hasPrivilege).as("check ReadOnly for Deal").isTrue();

        hasPrivilege = userRightsService.hasPrivilege(new Privilege("ReadWrite"), new User("rene.barjavel"), new Application("SLA"),
                PerimeterPath.builder().startsWith("Deal").build());
        assertions.assertThat(hasPrivilege).as("check ReadWrite for Deal").isFalse();

        // Check with path Deal/GLE
        hasPrivilege = userRightsService.hasPrivilege(new Privilege("ReadOnly"), new User("rene.barjavel"), new Application("SLA"),
                PerimeterPath.builder().startsWith("Deal").then("GLE").build());
        assertions.assertThat(hasPrivilege).as("check ReadOnly for Deal/GLE").isTrue();

        hasPrivilege = userRightsService.hasPrivilege(new Privilege("ReadWrite"), new User("rene.barjavel"), new Application("SLA"),
                PerimeterPath.builder().startsWith("Deal").then("GLE").build());
        assertions.assertThat(hasPrivilege).as("check ReadWrite for Deal").isTrue();

        assertions.assertAll();
    }*/
}
