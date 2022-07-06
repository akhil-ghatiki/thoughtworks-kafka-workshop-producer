package com.thoughtworks.kafka.workshop.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MarketPlaceEvent {
  private Integer marketPlaceEventId;
  private Product product;
}
