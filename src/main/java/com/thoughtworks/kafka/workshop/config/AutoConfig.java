package com.thoughtworks.kafka.workshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration // Enable this for autoConfiguration
@Profile("local")
public class AutoConfig {

  /**
   * The profile is set to local only.This auto config to create topics should never be used in
   * production environment
   */
  //  @Bean
  //  public NewTopic marketPlaceEvents() {
  //    return TopicBuilder.name("market-place-events").partitions(3).replicas(3).build();
  //  }
}
