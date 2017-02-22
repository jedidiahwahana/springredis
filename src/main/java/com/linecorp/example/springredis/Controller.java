
package com.linecorp.example.springredis;

import java.util.Map;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@RestController
public class Controller
{
    @Autowired
    private RedisTemplate<String, Object> template;
    
    @GetMapping(value="/")
    public ResponseEntity<String> hello(){
        
        System.out.println("getTestVal() function called++++++++++++++++++++");
        template.getConnectionFactory().getConnection().select(0);
        System.out.println("1----------");
        String pingResult = template.getConnectionFactory().getConnection().ping();
        System.out.println("2----------");
        System.out.println("REDIS PING RESULT: " + pingResult);
        System.out.println("3----------");
        System.out.println("HasKey Result Simple: " + template.hasKey("name"));
        System.out.println("4----------");
        System.out.println("HasKey Result Hash Map: " + template.hasKey("user:1"));
        System.out.println("5----------");
        System.out.println("HasKey Result List: " + template.hasKey("list"));
        System.out.println("6----------");
        Object testVal1 = template.opsForValue().get("name");
        System.out.println("TestVal1 returned from Redis: " + testVal1);
        Map<Object, Object> testVal2 = template.opsForHash().entries("user:1");
        for (Object key : testVal2.keySet())
            System.out.println("TestVal2 key: " + key + " TestVal2 value: " + testVal2.get(key));
        List<Object> testVal3 = template.opsForList().range("list", 0, template.opsForList().size("list"));
        for (int i=0; i<testVal3.size(); i++)
            System.out.println("TestVal3 value: " + testVal3.get(i));
        template.opsForValue().set("number", "1234567890");
        System.out.println("HasKey Result List: " + template.hasKey("number"));
        System.out.println("7----------");
        
        return new ResponseEntity<String>("{}", HttpStatus.OK);
    }
};
