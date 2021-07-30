package com.example.kafka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.kafka.service.ProducerService;

@RestController
@RequestMapping("/weather")
public final class KafkaController {
	
	
	public static String BASE_URL="http://localhost:8085/weather/";
	
    private final ProducerService producerService;
    
    @Autowired
    public RestTemplate restTemplate;

    public KafkaController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping(value = "/daily/{location}")
    public ResponseEntity<String> sendDailyWeatherDetails(@PathVariable("location") String location) {
    	Map<String, String> map = new HashMap();
    	map.put("location", location);
    	String response = restTemplate.getForObject(BASE_URL+"daily/{location}", String.class, map);
        producerService.sendMessage(response);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/historic/{location}")
    public ResponseEntity<String> sendHistoricWeatherDetails(@PathVariable("location") String location) {
    	Map<String, String> map = new HashMap();
    	map.put("location", location);
    	String response = restTemplate.getForObject(BASE_URL+"historic/{location}", String.class, map);
        producerService.sendMessage(response);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/future/{location}")
    public ResponseEntity<String> sendFutureWeatherDetails(@PathVariable("location") String location) {
    	Map<String, String> map = new HashMap();
    	map.put("location", location);
    	String response = restTemplate.getForObject(BASE_URL+"future/{location}", String.class, map);
        producerService.sendMessage(response);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
}
