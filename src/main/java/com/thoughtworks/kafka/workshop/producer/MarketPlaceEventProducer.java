package com.thoughtworks.kafka.workshop.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.kafka.workshop.data.MarketPlaceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

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

    listenableFuture.addCallback(
        new ListenableFutureCallback<SendResult<Integer, String>>() {

          @Override
          public void onFailure(Throwable ex) {
            log.error("Event sending success - key: {} - value: {}", key, value, ex);
          }

          @Override
          public void onSuccess(SendResult<Integer, String> result) {
            log.info(
                "Event sending success - key: {} - value: {} - metaData: {}",
                key,
                value,
                result.getRecordMetadata().toString());
          }
        });
  }

  public SendResult<Integer, String> sendMarketPlaceEventSynchronous(MarketPlaceEvent marketPlaceEvent) throws JsonProcessingException {
      Integer key = marketPlaceEvent.getMarketPlaceEventId();
      String value = objectMapper.writeValueAsString(marketPlaceEvent);
      SendResult<Integer,String> sendResult = null;

      try {
          sendResult  = kafkaTemplate.sendDefault(key,value).get();
      } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
      }

      return sendResult;
  }

  // TODO - exercise 1 - implement a producer to produce event into a specific topic.
}
