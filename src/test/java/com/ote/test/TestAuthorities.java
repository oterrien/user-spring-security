package com.ote.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestAuthorities {

    @Test
    public void testAuthorities() throws Exception {
        Authority authority = new Authority("SLA", "DEAL", "READ");
        log.warn(authority.toString());

        String json = JsonUtils.serialize(authority);
        log.warn(json);

        Authority authority2 = JsonUtils.parse(json, Authority.class);
        log.warn(authority2.toString());
    }

    @Test
    public void testUser() throws Exception {
        Authority authority = new Authority("SLA", "DEAL", "READ");
        String json = JsonUtils.serialize(authority);
        User user = new User("olivier.terrien", json);

        json = JsonUtils.serialize(user);
        log.warn(json);

        Authority authority2 = JsonUtils.parse(user.getAuthority(), Authority.class);
        log.warn(authority2.toString());
    }


}
