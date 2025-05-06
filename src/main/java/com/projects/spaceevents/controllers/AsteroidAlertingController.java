package com.projects.spaceevents.controllers;

import com.projects.spaceevents.services.AsteroidAlertingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/asteroid-alerting")
public class AsteroidAlertingController {

    private AsteroidAlertingService asteroidAlertingService;

    @Autowired
    public AsteroidAlertingController(AsteroidAlertingService asteroidAlertingService){
        this.asteroidAlertingService = asteroidAlertingService;
    }

    @PostMapping("/alert")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void alert() {
        asteroidAlertingService.alert();
    }

}
