package com.tuananhdo;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

public class BcPasswordEncodeTests {

    @Test
    public void testPasswordEncode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPassword = "password";
        String encodePassword = bCryptPasswordEncoder.encode(newPassword);
        System.out.println(encodePassword);
        boolean matches = bCryptPasswordEncoder.matches(newPassword,encodePassword);
        assertThat(matches).isTrue();
    }

}
