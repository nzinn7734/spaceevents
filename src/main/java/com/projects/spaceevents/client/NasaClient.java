package com.projects.spaceevents.client;

import com.projects.spaceevents.dto.Asteroid;
import com.projects.spaceevents.dto.NasaNeoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NasaClient {

    @Value("${nasa.neo.api.url}")
    private String nasaNeoApiUrl;

    @Value("${nasa.api.key}")
    private String nasaApiKey;

    public List<Asteroid> getNeoAsteroids(final LocalDate fromDate, final LocalDate toDate) {
        final RestTemplate restTemplate = new RestTemplate();
        final NasaNeoResponse nasaNeoResponse =
                restTemplate.getForObject(getUrl(fromDate, toDate), NasaNeoResponse.class);

        List<Asteroid> asteroidList = new ArrayList<>();
        if(nasaNeoResponse != null) {
            asteroidList.addAll(nasaNeoResponse.getNearEarthObjects().values().stream().flatMap(List::stream).toList());
        }

        return asteroidList;
    }

    public String getUrl(LocalDate fromDate, LocalDate toDate) {
        String apiUrl = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.nasa.gov")
                .path("/neo/rest/v1/feed")
                .query("?start_date={startDate}&end_date={endDate}&api_key={apiKey}")
                .buildAndExpand(fromDate, toDate, nasaApiKey)
                .toUriString();
        log.info("Nasa api url {}", apiUrl);
        return apiUrl;
    }

}
