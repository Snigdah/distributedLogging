package com.example.grandchildservice.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GrandChildController {
    private final GreandChildService greandChildService;

    public GrandChildController(GreandChildService greandChildService) {
        this.greandChildService = greandChildService;
    }

    @GetMapping("/grandchild")
    @Observed(
            name = "user.name",
            contextualName = "grandchild_service",
            lowCardinalityKeyValues = {"userType", "userType2"}
    )
    public String showService() {
        log.info("this is grand child service controller....");

        return greandChildService.childService();
    }
}
