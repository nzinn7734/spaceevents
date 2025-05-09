package com.projects.spaceevents.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsteroidCollisionEvent {
    private String asteroidName;
    private String closeApproachDate;
    private String missDistanceKilometers;
    private String missDistanceMiles;
    private double estimatedDiameterAvgMeters;
    private double estimatedDiameterAvgFeet;
}

