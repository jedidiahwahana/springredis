
package com.linecorp.example.springredis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import org.json.JSONObject;
import org.json.JSONArray;

@Configuration
public class Config
{
    
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {
        String sServices=System.getenv("VCAP_SERVICES");
        JSONObject jServices=new JSONObject(sServices);
        JSONArray aRedis=jServices.getJSONArray("compose-for-redis");
        JSONObject jRedis=aRedis.getJSONObject(0);
        JSONObject jCredentials=jRedis.getJSONObject("credentials");
        String dbUrl=jCredentials.getString("uri");
        String[] details = dbUrl.split("[:@]");
        
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
//        jedisConFactory.setHostName("localhost");
//        jedisConFactory.setPort(6379);

        jedisConFactory.setHostName(details[3]);
        jedisConFactory.setPort(Integer.parseInt(details[4]));
        jedisConFactory.setPassword(details[2]);
        return jedisConFactory;
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }
};
