package com.projects.spaceevents.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimatedDiameter {

    @JsonProperty("meters")
    private DiameterRange meters;

    @JsonProperty("feet")
    private DiameterRange feet;

}
