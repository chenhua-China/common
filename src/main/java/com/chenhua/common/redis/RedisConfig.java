package com.chenhua.common.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

  /**
   * Redis repository redis repository.
   *
   * @param redisTemplate the redis template
   * @return the redis repository
   */
  @ConditionalOnProperty(prefix = "spring.redis", name = "host")
  @Bean
  public RedisRepository redisRepository(RedisTemplate<String, String> redisTemplate) {
    return new RedisRepository(redisTemplate);
  }
}
