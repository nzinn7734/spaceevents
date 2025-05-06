package com.projects.spaceevents.services;

import com.projects.spaceevents.client.NasaClient;
import com.projects.spaceevents.dto.Asteroid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AsteroidAlertingService {

    private final NasaClient nasaClient;

    @Autowired
    public AsteroidAlertingService(NasaClient nasaClient) {
        this.nasaClient = nasaClient;
    }

    public void alert() {
        log.info("Asteroid alerting service called.");

        final LocalDate fromDate = LocalDate.now();
        final LocalDate toDate = LocalDate.now().plusDays(7);
        log.info("Getting asteroid list for date {} to {}", fromDate, toDate);

        final List<Asteroid> asteroidList = nasaClient.getNeoAsteroids(fromDate, toDate);

        log.info("Asteroid list: {}", asteroidList.toString());



    }

}
