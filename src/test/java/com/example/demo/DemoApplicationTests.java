package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

}
