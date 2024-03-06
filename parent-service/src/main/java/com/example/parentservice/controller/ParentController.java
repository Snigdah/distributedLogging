package com.example.parentservice.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ParentController {

    private final RestTemplate restTemplate;

    public ParentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/parent")
    @Observed(
            name = "user.name",
            contextualName = "parent_service",
            lowCardinalityKeyValues = {"userType", "userType2"}
    )
    public String callParent(){
        log.info("Parent was called");
        log.info("Say Hi to GrandChild");
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8081/child-service/child",
                HttpMethod.GET,
                null,
                String.class
        );

        return response.getBody();
    }
}
