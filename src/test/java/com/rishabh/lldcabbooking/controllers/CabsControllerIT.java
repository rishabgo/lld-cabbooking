package com.rishabh.lldcabbooking.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rishabh.lldcabbooking.LldCabbookingApplication;
import com.rishabh.lldcabbooking.database.CabsManager;
import com.rishabh.lldcabbooking.model.Cab;
import com.rishabh.lldcabbooking.model.ErrorResponse;
import com.rishabh.lldcabbooking.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = LldCabbookingApplication.class)
public class CabsControllerIT {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CabsManager cabsManager;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        Cab cab1 = Cab.builder().id("1").driverName("Rishabh").isAvailable(Boolean.TRUE).build();
        Cab cab2 = Cab.builder().id("2").driverName("Ranjan").isAvailable(Boolean.TRUE).build();
        cabsManager.createCab(cab1);
        cabsManager.createCab(cab2);
    }

    @Test
    public void testRegisterCab() throws JsonProcessingException {
        Cab cab = createCab();
        String jsonRequest = objectMapper.writeValueAsString(cab);
        String postURL = getUrl("/api/v1/rest/register/cab");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Cab> responseEntity = restTemplate.exchange(postURL, HttpMethod.POST, new HttpEntity<>(jsonRequest, headers), Cab.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getDriverName()).isEqualTo("Rishabh");
    }


    @Test
    public void testRegisterCabWithAlreadyExistingCabId() throws JsonProcessingException {

        Cab cab = Cab.builder().id("1").driverName("Rishabh").isAvailable(Boolean.TRUE).build();
        String jsonRequest = objectMapper.writeValueAsString(cab);
        String postURL = getUrl("/api/v1/rest/register/cab");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ErrorResponse> responseEntity = restTemplate.exchange(postURL, HttpMethod.POST, new HttpEntity<>(jsonRequest, headers), ErrorResponse.class);
       /* Exception exception = assertThrows(CabAlreadyExistsException.class, () -> {
            restTemplate.exchange(postURL, HttpMethod.POST, new HttpEntity<>(jsonRequest, headers), Cab.class);
        });*/
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateCabLocation() throws JsonProcessingException {
        String postURL = getUrl("/api/v1/rest/update/cab/location/cabId/1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Location updatedLocation = new Location(123d, 345d);
        String jsonRequest = objectMapper.writeValueAsString(updatedLocation);
        ResponseEntity<Cab> response = restTemplate.exchange(postURL, HttpMethod.POST, new HttpEntity<>(jsonRequest, headers), Cab.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCurrentLocation()).isEqualTo(updatedLocation);
    }

    @Test
    public void testUpdateCabAvailability() throws JsonProcessingException {
        /*String postURL = getUrl("/api/v1/rest/update/cab/availability");*/
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Cab updatedCab = Cab.builder().id("1").driverName("rishabh").isAvailable(false).build();
        String jsonRequest = objectMapper.writeValueAsString(updatedCab);
        ResponseEntity<Cab> response = restTemplate.exchange("/api/v1/rest/update/cab/availability", HttpMethod.POST, new HttpEntity<>(jsonRequest, headers), Cab.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getIsAvailable()).isEqualTo(Boolean.FALSE);
    }

    private String getUrl(String url) {
        return UriComponentsBuilder.fromUriString("http://localhost:" + port + url).toUriString();
    }

    public Cab createCab() {
        Cab cab = Cab.builder().id(UUID.randomUUID().toString()).driverName("Rishabh").build();
        return cab;
    }


}
