package com.thoughtworks.kafka.workshop.controller;

import com.thoughtworks.kafka.workshop.data.MarketPlaceEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/market-place")
public class MarketPlaceController {

    @PostMapping("/event")
    public ResponseEntity<MarketPlaceEvent> postMarketPlaceEvent(@RequestBody MarketPlaceEvent marketPlaceEvent){
        // publish the message to kafka broker
        return ResponseEntity.status(HttpStatus.CREATED).body(marketPlaceEvent);
    }
}
