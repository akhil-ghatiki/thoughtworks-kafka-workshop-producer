package com.thoughtworks.kafka.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketPlaceEventProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MarketPlaceEventProducerApplication.class, args);
  }
}
