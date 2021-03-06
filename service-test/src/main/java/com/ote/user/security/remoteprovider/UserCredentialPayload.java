package com.ote.user.security.remoteprovider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialPayload {

    private String user;

    private boolean isPasswordCorrect;

}
