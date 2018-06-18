package redcloud.services;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String doSomething() {
//        return UUID.randomUUID().toString();
        throw new IllegalArgumentException("Custom error");
    }
}
