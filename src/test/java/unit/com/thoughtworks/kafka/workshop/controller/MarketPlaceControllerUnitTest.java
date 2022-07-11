package com.thoughtworks.kafka.workshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.kafka.workshop.data.MarketPlaceEvent;
import com.thoughtworks.kafka.workshop.data.Product;
import com.thoughtworks.kafka.workshop.producer.MarketPlaceEventProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class MarketPlaceControllerUnitTest {

    @Mock
    MarketPlaceEventProducer marketPlaceEventProducer;
    @InjectMocks MarketPlaceController marketPlaceController;

    @Test
    public void shouldReturnHttpStatusCreated() throws JsonProcessingException {
        Product product =
                Product.builder()
                        .productId(3)
                        .productName("integration-name")
                        .productBrand("integration-brand")
                        .build();
        MarketPlaceEvent marketPlaceEvent =
                MarketPlaceEvent.builder().marketPlaceEventId(9).product(product).build();

        HttpStatus httpStatus = marketPlaceController.postMarketPlaceEvent(marketPlaceEvent).getStatusCode();

        verify(marketPlaceEventProducer,times(1)).sendMarketPlaceEvent(any());
        assertEquals(HttpStatus.CREATED, httpStatus);
    }

}
