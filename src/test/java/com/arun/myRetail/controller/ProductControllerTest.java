/**
 * 
 */
package com.arun.myRetail.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.arun.myRetail.service.ProductService;

/**
 * @author Arun
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerTest {

	@InjectMocks
	ProductController productController;
	
	@Mock
	ProductService productService;
	
	MockMvc mockMvc;
	
	int productId = 13860428;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@Test
	public void getProduct() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/products/v1/" + productId))
								.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void testUpdateProduct() throws Exception {
		MvcResult mvcResult = mockMvc.perform(put("/products/v1/" + productId)
			.accept(MediaType.APPLICATION_JSON)
			.content("{}")
			.contentType(MediaType.APPLICATION_JSON))
        	.andExpect(status().isOk()).andReturn();
	}
	
}
