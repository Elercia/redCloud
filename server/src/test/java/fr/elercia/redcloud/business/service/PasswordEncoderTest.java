package fr.elercia.redcloud.business.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    @Test
    void encode_verify() {
        String password = "password";
        assertTrue(PasswordEncoder.matches(password, PasswordEncoder.encode(password)));
    }
}