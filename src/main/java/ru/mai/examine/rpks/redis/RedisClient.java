package ru.mai.examine.rpks.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisClient {

    private Jedis jedis;


    public void connect(@Value("${redis.host}") String host,
                        @Value("${redis.port}") int port) {
        jedis = new Jedis(host, port);
        jedis.ping();
    }

    public boolean keyExists(String key) {
        return jedis.exists(key);
    }

    public Optional<String> getByKey(String key) {
        if (key == null || key.isEmpty() || !keyExists(key)) {
            return Optional.empty();
        }
        return Optional.ofNullable(jedis.get(key));
    }

    public void shutDown() {
        if (jedis != null) {
            try {
                if (jedis.isConnected()) {
                    jedis.disconnect();
                }
                jedis.close();
            } catch (Exception ignored) {
            }
        }
    }
}
