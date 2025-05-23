package com.projects.spaceevents.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpaceWeatherAlertingService {

    public void alert() {
        log.info("Space weather alert service called.");
    }
}
