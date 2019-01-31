package fr.elercia.redcloud;

import fr.elercia.redcloud.business.service.PasswordEncoder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Disabled
public class NoTest {

    @Test
    void test() {
        System.out.println(PasswordEncoder.encode("password"));
        System.out.println(UUID.randomUUID());
    }
}
