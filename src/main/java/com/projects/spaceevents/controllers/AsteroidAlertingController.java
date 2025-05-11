package com.projects.spaceevents.controllers;

import com.projects.spaceevents.services.AsteroidAlertingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/asteroid-alerting")
public class AsteroidAlertingController {

    private AsteroidAlertingService asteroidAlertingService;

    @Autowired
    public AsteroidAlertingController(AsteroidAlertingService asteroidAlertingService){
        this.asteroidAlertingService = asteroidAlertingService;
    }

    @GetMapping("/health/live")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String health() {
        log.info("Health check endpoint called.");
        return "ASTEROID ALERT LIVE";
    }

    @PostMapping("/alert")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void alert() {
        log.info("Asteroid alerting controller alert called.");
        asteroidAlertingService.alert();
    }

}
