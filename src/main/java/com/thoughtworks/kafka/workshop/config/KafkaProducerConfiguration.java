package com.thoughtworks.kafka.workshop.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration // Enable this to take configuration from factory
public class KafkaProducerConfiguration {

    /** This should not be used when the auto config is used. Refer AutoConfig.java
     * Use either AutoConfig / producer factory.
     * */

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        Map<String,Object> properties = new HashMap<>();
        properties.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092,localhost:9093,localhost:9094");
        properties.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class);
        properties.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        //TODO - exercise - Update the topic market-place-events configuration using console.
        // set min.insync.replicas as 3.



        // TODO - exercise - Add below broker properties using configuration
        //      acks=all
        //      retries=5
        //      retry back off = 1000


        // TODO - exercise -
        //  1. Produce the payload to the topic.
        //  2. Now kill one broker among 3 (use ctrl+c to kill) and try producing the payload.
        //  3. Observe the behaviour.
        //  4. Bring back the killed broker and produce the payload.
        //  5. Observe the behaviour

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
