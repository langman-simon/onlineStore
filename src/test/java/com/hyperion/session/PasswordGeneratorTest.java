package com.hyperion.session;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordGeneratorTest {

    @Test
    void generatePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hash = encoder.encode("mdp");

        System.out.println("Hash : " + hash);
        System.out.println("Longueur : " + hash.length());
        System.out.println("Correspond : " + encoder.matches("mdp", hash));
    }
}