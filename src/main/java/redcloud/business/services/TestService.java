package redcloud.business.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestService {

    public String getInfo() {
        return UUID.randomUUID().toString();
    }
}
