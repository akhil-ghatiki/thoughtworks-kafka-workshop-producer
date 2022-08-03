package com.thoughtworks.kafka.workshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.kafka.workshop.data.MarketPlaceEvent;
import com.thoughtworks.kafka.workshop.producer.MarketPlaceEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market-place")
public class MarketPlaceController {

  @Autowired MarketPlaceEventProducer marketPlaceEventProducer;

  @PostMapping("/event")
  public ResponseEntity<MarketPlaceEvent> postMarketPlaceEvent(
      @RequestBody MarketPlaceEvent marketPlaceEvent) throws JsonProcessingException {

    marketPlaceEventProducer.sendMarketPlaceEventAsynchronous(marketPlaceEvent);
    // TODO - exercise 0 - try using synchronous producer here.

    return ResponseEntity.status(HttpStatus.CREATED).body(marketPlaceEvent);
  }
}
