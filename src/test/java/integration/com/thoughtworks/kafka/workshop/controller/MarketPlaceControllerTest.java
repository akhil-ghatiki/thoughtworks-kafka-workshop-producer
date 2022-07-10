package com.thoughtworks.kafka.workshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.thoughtworks.kafka.workshop.data.MarketPlaceEvent;
import com.thoughtworks.kafka.workshop.data.Product;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(
    topics = {"market-place-events"},
    partitions = 3)
@TestPropertySource(
    properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class MarketPlaceControllerTest {

  @Autowired private TestRestTemplate testRestTemplate;

  @Autowired EmbeddedKafkaBroker embeddedKafkaBroker;

  private Consumer<Integer, String> consumer;

  @BeforeEach
  void setup() {
    Map<String, Object> consumerConfig =
        new HashMap<>(KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker));

    consumer =
        new DefaultKafkaConsumerFactory<>(
                consumerConfig, new IntegerDeserializer(), new StringDeserializer())
            .createConsumer();
  }

  @Test
  public void postMarketPlaceEvent() throws InterruptedException {
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

    // TODO - consume the record and evaluate if the correct payload was produced.

    // TODO - [Subtask - ONLY after above `TODO` is completed]
    // Think what could potentially go wrong when you consume the payload here

  }

  @AfterEach
  public void tearDown() {
    // TODO - its a good practice to close the consumer after each test case.
  }
}
