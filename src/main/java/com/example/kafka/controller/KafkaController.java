package com.example.kafka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.kafka.service.ProducerService;

@CrossOrigin
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

    @GetMapping(value = "/daily/{location}")
    public ResponseEntity<String> sendDailyWeatherDetails(@PathVariable("location") String location) {
    	System.out.println("inside daily kafka producer");
    	Map<String, String> map = new HashMap();
    	map.put("location", location);
    	String response = restTemplate.getForObject(BASE_URL+"daily/{location}", String.class, map);
        producerService.sendMessage(response);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/historic/{location}")
    public ResponseEntity<String> sendHistoricWeatherDetails(@PathVariable("location") String location) {
    	System.out.println("inside historic kafka producer");
    	Map<String, String> map = new HashMap();
    	map.put("location", location);
    	String response = restTemplate.getForObject(BASE_URL+"historic/{location}", String.class, map);
        producerService.sendMessage(response);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/future/{location}")
    public ResponseEntity<String> sendFutureWeatherDetails(@PathVariable("location") String location) {
    	System.out.println("inside future kafka producer");
    	Map<String, String> map = new HashMap();
    	map.put("location", location);
    	String response = restTemplate.getForObject(BASE_URL+"future/{location}", String.class, map);
        producerService.sendMessage(response);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
}
