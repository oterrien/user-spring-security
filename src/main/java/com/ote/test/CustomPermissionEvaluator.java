package com.ote.test;

import lombok.Data;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication auth, Object perimeter, Object permission) {
        if ((auth == null) || (perimeter == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, perimeter.toString(), permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String perimeter, Object permission) {
        if ((auth == null) || (perimeter == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, perimeter, permission.toString());
    }

    private boolean hasPrivilege(Authentication auth, String perimeter, String permission) {

        UserRight userRight = new UserRight(perimeter, permission);

        return auth.getAuthorities().stream().
                map(p -> new UserRight(p.getAuthority())).
                anyMatch(userRight::equals);
    }

    @Data
    private class UserRight {
        private final String perimeter;
        private final boolean perimeterWildcards;
        private final String privilege;

        public UserRight(String authority) {
            String[] part = authority.split("_");
            perimeter = part[0];
            privilege = part[1];
            perimeterWildcards = false;
        }

        public UserRight(String perimeter, String privilege) {
            if (perimeter.endsWith("*")) {
                this.perimeter = perimeter.substring(0, perimeter.length() - 1);
                this.perimeterWildcards = true;
            } else {
                this.perimeter = perimeter;
                this.perimeterWildcards = false;
            }
            this.privilege = privilege;
        }

        @Override
        public boolean equals(Object object) {
            return (object instanceof UserRight) && equals((UserRight) object);
        }

        private boolean equals(UserRight userRight) {

            return this.privilege.equals(userRight.getPrivilege()) &&
                    (this.perimeterWildcards ?
                            userRight.perimeter.startsWith(this.perimeter) :
                            userRight.perimeter.equals(this.perimeter));

        }
    }
}