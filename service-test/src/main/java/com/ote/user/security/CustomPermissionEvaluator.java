package com.ote.user.security;

import com.ote.user.security.remoteprovider.RemoteUserRightProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final RemoteUserRightProvider remoteUserRightProvider;

    @Override
    public boolean hasPermission(Authentication auth, Object perimeter, Object permission) {
        return hasPrivilege(auth, perimeter.toString(), permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String perimeter, Object permission) {
        return hasPrivilege(auth, perimeter, permission.toString());
    }

    private boolean hasPrivilege(Authentication auth, String perimeter, String permission) {
        return remoteUserRightProvider.doesUserOwnPrivilegeForApplicationOnPerimeter(auth.getName(), perimeter, permission);
    }

}