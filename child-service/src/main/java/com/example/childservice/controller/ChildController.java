package com.example.childservice.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ChildController {

    private final RestTemplate restTemplate;

    public ChildController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/child")
    @Observed(
            name = "user.name",
            contextualName = "child_service",
            lowCardinalityKeyValues = {"userType", "userType2"}
    )
    public String callChild(){
        log.info("Child was called ...");
        log.info("Calling GrandChild now ....");
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/grandchild-service/grandchild",
                HttpMethod.GET,
                null,
                String.class
        );
        return response.getBody();
    }
}
