package com.thoughtworks.kafka.workshop.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.kafka.workshop.data.MarketPlaceEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class MarketPlaceEventProducer {

  @Autowired KafkaTemplate<Integer, String> kafkaTemplate;

  @Autowired ObjectMapper objectMapper;

  public void sendMarketPlaceEvent(MarketPlaceEvent marketPlaceEvent)
      throws JsonProcessingException {

    Integer key = marketPlaceEvent.getMarketPlaceEventId();
    String value = objectMapper.writeValueAsString(marketPlaceEvent);

    ListenableFuture<SendResult<Integer, String>> listenableFuture =
        kafkaTemplate.sendDefault(key, value);

    listenableFutureCallback(key, value, listenableFuture);
  }

  public SendResult<Integer, String> sendMarketPlaceEventSynchronous(
      MarketPlaceEvent marketPlaceEvent) throws JsonProcessingException {
    Integer key = marketPlaceEvent.getMarketPlaceEventId();
    String value = objectMapper.writeValueAsString(marketPlaceEvent);
    SendResult<Integer, String> sendResult = null;

    try {
      sendResult = kafkaTemplate.sendDefault(key, value).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return sendResult;
  }

  // TODO - exercise 1 - implement a producer to publish event into a specific topic.

 // TODO - exercise 4  - publish an event into kafka with a key and check how they end up into partitions

  private void listenableFutureCallback(
      Integer key, String value, ListenableFuture<SendResult<Integer, String>> listenableFuture) {
    listenableFuture.addCallback(
        new ListenableFutureCallback<>() {
          @Override
          public void onFailure(Throwable ex) {
            log.error("Failed to send the event - key: {} - value: {}", key, value, ex);
          }

          @Override
          public void onSuccess(SendResult<Integer, String> result) {
            log.info(
                "Event sending successful - key: {} - value: {} - metaData: {}",
                key,
                value,
                result.getRecordMetadata().toString());
          }
        });
  }
}
