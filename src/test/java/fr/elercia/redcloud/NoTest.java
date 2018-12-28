package fr.elercia.redcloud;

import fr.elercia.redcloud.business.service.PasswordEncoder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class NoTest {

    @Test
    void test() {
        System.out.println(PasswordEncoder.encode("password"));
        System.out.println(UUID.randomUUID());
    }
}
