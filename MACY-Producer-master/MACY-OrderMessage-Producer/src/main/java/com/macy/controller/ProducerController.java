package com.macy.controller;

import static util.Constant.EXCHANGE;
import static util.Constant.ROUTING_JSON_KEY;
import static util.Constant.ROUTING_XML_KEY;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macy.dto.FulFillmentOrder;
import com.macy.dto.OrderJsonDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("macy/producer/")
public class ProducerController {
	
	@Autowired
	RabbitTemplate rabbitJsonTemplate;
	@Autowired
	RabbitTemplate rabbitXmlTemplate;

	@ApiOperation(value = "Order as Json Input")
	@PostMapping(value = "order/json", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String getOrderInJson(@RequestBody OrderJsonDto order) {

		rabbitJsonTemplate.convertAndSend(EXCHANGE, ROUTING_JSON_KEY, order);
		return "Success Json !!";

	}

	@ApiOperation(value = "Order as xml Input")
	@PostMapping(value = "order/xml",consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { 
					MediaType.APPLICATION_JSON_VALUE })
	public String getOrderInXml(@RequestBody FulFillmentOrder fulFillmentOrder) {

		rabbitXmlTemplate.convertAndSend(EXCHANGE, ROUTING_XML_KEY, fulFillmentOrder);
		return "Success XML !!";

	}

}
