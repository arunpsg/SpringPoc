/**
 * 
 */
package com.arun.myRetail.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author Arun
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerTest {

	@InjectMocks
	ProductController productController;
	
	MockMvc mockMvc;
	
	String productId = "";
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@Test
	public void getProduct() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/products/v1/" + productId))
		.andExpect(status().isOk()).andReturn();
		
	}
	
	
	
}
