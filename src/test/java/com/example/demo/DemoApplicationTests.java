package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest
public class DemoApplicationTests {

	@Autowired
	private MockMvc mvc;

	// testing /math/pi endpoint
	@Test
	public void testPiEndpoint() throws Exception {
		this.mvc.perform(get("/math/pi").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().string("3.141592653589793"));
	}

	// testing MathService class
	@Test
	public void testMathServiceCalculateAdd() throws Exception {
		this.mvc.perform(get("/math/calculate/?operation=add&x=1&y=2"))
				.andExpect(status().isOk())
				.andExpect(content().string("1 + 2 = 3"));
	}

	@Test
	public void testMathServiceCalculateSubtract() throws Exception {
		this.mvc.perform(get("/math/calculate/?operation=subtract&x=3&y=2"))
				.andExpect(status().isOk())
				.andExpect(content().string("3 - 2 = 1"));
	}

	@Test
	public void testMathServiceCalculateMultiply() throws Exception {
		this.mvc.perform(get("/math/calculate/?operation=multiply&x=2&y=3"))
				.andExpect(status().isOk())
				.andExpect(content().string("2 x 3 = 6"));
	}

	@Test
	public void testMathServiceCalculateDivide() throws Exception {
		this.mvc.perform(get("/math/calculate/?operation=divide&x=4&y=2"))
				.andExpect(status().isOk())
				.andExpect(content().string("4 / 2 = 2"));
	}

	@Test
	public void testMathServiceSum() throws Exception {
		this.mvc.perform(post("/math/sum/?n=2&n=3&n=6"))
				.andExpect(status().isOk())
				.andExpect(content().string("2 + 3 + 6 = 11"));
	}

	// Unit 3, exercise 3 tests
	@Test
	public void testMathServiceVolumePost() throws Exception {
		this.mvc.perform(post("/math/volume/3/4/5"))
				.andExpect(status().isOk())
				.andExpect(content().string("The volume of a 3x4x5 rectangle is 60"));
	}

	@Test
	public void testMathServiceVolumePatch() throws Exception {
		this.mvc.perform(patch("/math/volume/6/7/8"))
				.andExpect(status().isOk())
				.andExpect(content().string("The volume of a 6x7x8 rectangle is 336"));
	}

	// Unit 3, exercise 4 tests
	@Test
	public void testMathServiceAreaCircle() throws Exception {
		this.mvc.perform(post("/math/area/?type=circle&radius=4"))
				.andExpect(status().isOk())
				.andExpect(content().string("Area of a circle with a radius of 4 is 39.4784"));
	}

	@Test
	public void testMathServiceAreaRectangle() throws Exception {
		this.mvc.perform(post("/math/area/?type=rectangle&width=4&height=7"))
				.andExpect(status().isOk())
				.andExpect(content().string("Area of a 4 x 7 rectangle is 28"));
	}

	@Test
	public void testMathServiceAreaInvalid() throws Exception {
		this.mvc.perform(post("/math/area/?type=rectangle&radius=5"))
				.andExpect(status().isOk())
				.andExpect(content().string("Is Invalid"));
	}

	// UNit 4, Flight control tests
	@Test
	public void testFlightsSingleEndpoint() throws Exception {
		this.mvc.perform(
				get("/flights/flight")
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.Departs", is("2017-04-21 14:34")))
				.andExpect(jsonPath("$.Tickets[0].Passenger.FirstName", is("Some")))
				.andExpect(jsonPath("$.Tickets[0].Passenger.LastName", is("Name")))
				.andExpect(jsonPath("$.Tickets[0].Price", is(200)));
	}

	@Test
	public void testFlightsAllEndpoint() throws Exception {

		// get first flight
		this.mvc.perform(
				get("/flights")
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].Departs", is("2017-04-21 14:34")))
				.andExpect(jsonPath("$[0].Tickets[0].Passenger.FirstName", is("Some")))
				.andExpect(jsonPath("$[0].Tickets[0].Passenger.LastName", is("Name")))
				.andExpect(jsonPath("$[0].Tickets[0].Price", is(200)));

		// get second flight
		this.mvc.perform(
				get("/flights")
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[1].Departs", is("2017-04-21 14:34")))
				.andExpect(jsonPath("$[1].Tickets[0].Passenger.FirstName", is("SomeOther")))
				.andExpect(jsonPath("$[1].Tickets[0].Passenger.LastName", isEmptyOrNullString()))
				.andExpect(jsonPath("$[1].Tickets[0].Price", is(400)));
	}

	//Unit 4, Exercise 3
	@Test
	public void getFlightsTicketTotal() throws Exception {

		String json = getJSON("/tickets.json");

		MockHttpServletRequestBuilder request = post("/flights/tickets/total")
			.contentType(MediaType.APPLICATION_JSON)
		    .content(json);

		this.mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(350)));
	}

	// utility for retrieving JSON
	private String getJSON(String path) throws Exception {
		URL url = this.getClass().getResource(path);
		return new String(Files.readAllBytes(Paths.get(url.getFile())));
	}
}
