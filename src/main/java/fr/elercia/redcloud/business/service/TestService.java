package fr.elercia.redcloud.business.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String doSomething() {
//        return UUID.randomUUID().toString();
        throw new IllegalArgumentException("Custom error");
    }
}
