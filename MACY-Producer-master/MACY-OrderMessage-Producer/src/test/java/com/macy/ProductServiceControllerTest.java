package com.macy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.macy.dto.OrderJsonDto;

public class ProductServiceControllerTest extends AbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	 @Test
	   public void testcreateOrder() throws Exception {
	      String uri = "http://localhost:9001/macy/producer/order/json";
	      OrderJsonDto order = new OrderJsonDto();
	      order.setId(1);
	      order.setItemName("Book");;
	      String inputJson = super.mapToJson(order);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      
	      assertEquals(200, status);
	     
	      String content = mvcResult.getResponse().getContentAsString();
	   }
}