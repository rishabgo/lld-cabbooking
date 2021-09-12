package com.rishabh.lldcabbooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rishabh.lldcabbooking.database.CabsManager;
import com.rishabh.lldcabbooking.database.TripsManager;
import com.rishabh.lldcabbooking.model.Cab;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CabsController.class)
class CabsControllerTest {

    private static final String BASE_URL = "/api/v1/rest";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CabsManager cabsManager;

    @MockBean
    private TripsManager tripsManager;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test registration of new cab")
    void registerCab() throws Exception {
        Cab cab = createCab();
        Mockito.when(cabsManager.createCab(any())).thenReturn(cab);
        String jsonRequest = objectMapper.writeValueAsString(cab);
        mockMvc.perform(post(BASE_URL + "/register/cab")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.driverName",Matchers.is("Rishabh")));

    }

    /*@Test
    void registerCabWithAlreadyExistingCabId(){

        Cab cab = createCab();
        Mockito.when(cabsManager.createCab(any())).thenReturn(cab);
        String jsonRequest = objectMapper.writeValueAsString(cab);
        mockMvc.perform(post(BASE_URL + "/register/cab")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].driverName",Matchers.is("Rishabh")));


    }*/

    @Test
    @DisplayName("Test Updating cab location")
    void updateCabLocation() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cabId", UUID.randomUUID().toString());
        jsonObject.put("newX",1.0);
        jsonObject.put("newY",1.0);

        String jsonString = jsonObject.toString();

        mockMvc.perform(post(BASE_URL + "/update/cab/location")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("Test availability update of the cab")
    void updateCabAvailability() {
    }

    @Test
    @DisplayName("Test end trip")
    void endTrip() {
    }

    public Cab createCab(){
       Cab cab = Cab.builder().id(UUID.randomUUID().toString()).driverName("Rishabh").build();
       return cab;
    }
}