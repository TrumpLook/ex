package ru.mai.examine.rpks.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.examine.rpks.redis.RedisClient;
import ru.mai.examine.rpks.service.KafkaConsumerService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final RedisClient redisClient;
    private final KafkaConsumerService consumerService;

    @GetMapping("/get")
    public ResponseEntity<String> createConsumerAndFetchFromRedisAndKafka(
            @RequestParam String bootstrapServers,
            @RequestParam String topic
    ) {
        if (bootstrapServers == null || bootstrapServers.isEmpty() || topic == null || topic.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<String> redisKey = consumerService.createConsumerAndGetValue(bootstrapServers, topic);

        if (redisKey.isPresent()) {
            log.warn("redis key not found");
            ResponseEntity.badRequest().build();
        }
        Optional<String> valueByKey = redisClient.getByKey(redisKey.get());

        if (valueByKey.isEmpty()) {
            log.warn("No value found for key {}", redisKey.get());
            ResponseEntity.badRequest().build();
        }

        String trans = new StringBuilder(valueByKey.get().toUpperCase()).reverse().toString();

        return ResponseEntity.ok(trans);
    }
}
