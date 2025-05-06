package com.projects.spaceevents.controllers;

import com.projects.spaceevents.services.SpaceWeatherAlertingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/space-weather-alerting")
public class SpaceWeatherAlertController {

    private final SpaceWeatherAlertingService spaceWeatherAlertingService;

    @Autowired
    public SpaceWeatherAlertController(SpaceWeatherAlertingService spaceWeatherAlertingService) {
        this.spaceWeatherAlertingService = spaceWeatherAlertingService;
    }

    @GetMapping("/health/live")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String health() {
        log.info("Health check endpoint called.");
        return "LIVE";
    }

    @PostMapping("/alert")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void alert() {
        spaceWeatherAlertingService.alert();
    }
}
