package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FlightServiceTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testFlightsSingleEndpoint() throws Exception {
        this.mvc.perform(
                get("/flight")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departs", is("2017-04-21 14:35")))
                .andExpect(jsonPath("$.tickets[0].passenger.firstName", is("Some")))
                .andExpect(jsonPath("$.tickets[0].passenger.lastName", is("Name")))
                .andExpect(jsonPath("$.tickets[0].price", is(200)));
    }

    @Test
    public void testFlightsAllEndpoint() throws Exception {

        // get first flight
        this.mvc.perform(
                get("/")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$[0].tickets[0].passenger.firstName", is("Some")))
                .andExpect(jsonPath("$[0].tickets[0].passenger.lastName", is("Name")))
                .andExpect(jsonPath("$[0].tickets[0].price", is(200)));

        // get second flight
        this.mvc.perform(
                get("")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].departs", is("2017-04-21 14:34")))
                .andExpect(jsonPath("$[1].tickets[0].passenger.firstName", is("SomeOther")))
                .andExpect(jsonPath("$[1].tickets[0].passenger.lastName", isEmptyOrNullString()))
                .andExpect(jsonPath("$[1].tickets[0].price", is(400)));
    }

}
