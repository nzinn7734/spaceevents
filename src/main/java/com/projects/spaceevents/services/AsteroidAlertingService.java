package com.projects.spaceevents.services;

import com.projects.spaceevents.client.NasaClient;
import com.projects.spaceevents.dto.Asteroid;
import com.projects.spaceevents.event.AsteroidCollisionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AsteroidAlertingService {

    private final NasaClient nasaClient;
    private final KafkaTemplate<String, AsteroidCollisionEvent> kafkaTemplate;

    @Autowired
    public AsteroidAlertingService(NasaClient nasaClient, KafkaTemplate<String, AsteroidCollisionEvent> kafkaTemplate) {
        this.nasaClient = nasaClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void alert() {
        log.info("Asteroid alerting service called.");

        final LocalDate fromDate = LocalDate.now();
        final LocalDate toDate = LocalDate.now().plusDays(7);
        log.info("Getting asteroid list for date {} to {}", fromDate, toDate);

        final List<Asteroid> asteroidList = nasaClient.getNeoAsteroids(fromDate, toDate);

        log.info("Asteroid list: {}", asteroidList.toString());

        final List<Asteroid> dangerousAsteroids = asteroidList.stream()
                        .filter(Asteroid::isPotentiallyHazardous)
                        .toList();

        log.info("Found {} hazardous asteroids", dangerousAsteroids.size());

        final List<AsteroidCollisionEvent> asteroidCollisionEventList =
                createEventListOfDangerousAsteroids(dangerousAsteroids);

        log.info("Sending {} asteroid alerts to kafka", asteroidCollisionEventList.size());

        asteroidCollisionEventList.forEach(event -> {
            kafkaTemplate.send("asteroid-alert", event);
            log.info("Asteroid alert sent to Kafka topic {}", event);
        });
    }

    private List<AsteroidCollisionEvent> createEventListOfDangerousAsteroids(List<Asteroid> dangerousAsteroids) {
        return dangerousAsteroids.stream()
                .map(asteroid -> {
                    if(asteroid.isPotentiallyHazardous()) {
                      return AsteroidCollisionEvent.builder()
                              .asteroidName(asteroid.getName())
                              .closeApproachDate(asteroid.getCloseApproachData().getFirst().getCloseApproachDate().toString())
                              .missDistanceKilometers(asteroid.getCloseApproachData().getFirst().getMissDistance().getKilometers())
                              .missDistanceMiles(asteroid.getCloseApproachData().getFirst().getMissDistance().getMiles())
                              .estimatedDiameterAvgMeters((asteroid.getEstimatedDiameter().getMeters().getMinDiameter() +
                                      asteroid.getEstimatedDiameter().getMeters().getMaxDiameter())/2)
                              .estimatedDiameterAvgFeet((asteroid.getEstimatedDiameter().getFeet().getMinDiameter() +
                                      asteroid.getEstimatedDiameter().getFeet().getMaxDiameter())/2)
                              .build();
                    }
                    return null;
                }).toList();
    }

}
