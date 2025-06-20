package ru.mai.examine.rpks.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.Property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

/**
 * @since 20.06.2025
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private Properties props = new Properties();

    @PostConstruct
    public void init() {
        props.put("group.id", "task-consumer-1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");
    }

    @Value("${app.kafka.consumer.timeout}")
    private int timeoutSeconds;

    public Optional<String> createConsumerAndGetValue(
            final String bootstrapServers,
            final String topic) {
        if (bootstrapServers == null || bootstrapServers.isEmpty() || topic == null || topic.isEmpty()) {
            log.error("invalid args: {} {}", bootstrapServers, topic);
            return Optional.empty();
        }

        props.put("bootstrap.servers", bootstrapServers);

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(topic));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(timeoutSeconds));

            if (records.isEmpty()) {
                log.warn("can not found any records from kafka topic: {}", topic);
                return Optional.empty();
            }

            final ConsumerRecord<String, String> first = records.iterator().next();
            return Optional.ofNullable(first.value());
        } catch (Exception e) {
            log.error("some exception occured: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
