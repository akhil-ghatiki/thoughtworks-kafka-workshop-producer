package com.thoughtworks.kafka.workshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.thoughtworks.kafka.workshop.data.MarketPlaceEvent;
import com.thoughtworks.kafka.workshop.data.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(
    topics = {"market-place-events"},
    partitions = 3)
@TestPropertySource(
    properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class MarketPlaceControllerTest {

  @Autowired TestRestTemplate testRestTemplate;

  @Test
  public void postMarketPlaceEvent() {
    Product product =
        Product.builder()
            .productId(3)
            .productName("integration-name")
            .productBrand("integration-brand")
            .build();
    MarketPlaceEvent marketPlaceEvent =
        MarketPlaceEvent.builder().marketPlaceEventId(9).product(product).build();

    HttpHeaders headers = new HttpHeaders();
    headers.set("content-type", MediaType.APPLICATION_JSON.toString());
    HttpEntity<MarketPlaceEvent> httpRequest = new HttpEntity<>(marketPlaceEvent, headers);
    ResponseEntity<MarketPlaceEvent> responseEntity =
        testRestTemplate.exchange(
            "/market-place/event", HttpMethod.POST, httpRequest, MarketPlaceEvent.class);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }
}
