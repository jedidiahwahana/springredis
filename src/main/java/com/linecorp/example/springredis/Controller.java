
package com.linecorp.example.springredis;

import java.util.*;
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
        System.out.println("HasKey Result Hash Map: " + template.hasKey("tutorialspoint"));
        System.out.println("5----------");
        System.out.println("HasKey Result List: " + template.hasKey("tutorials"));
        System.out.println("6----------");
        
        Object testVal1 = template.opsForValue().get("name");
        System.out.println("TestVal1 returned from Redis: " + testVal1);
        
        Map<Object, Object> testVal2 = template.opsForHash().entries("tutorialspoint");
        for (Object key : testVal2.keySet())
            System.out.println("TestVal2 key: " + key + " TestVal2 value: " + testVal2.get(key));
        
        List<Object> testVal3 = template.opsForList().range("tutorials", 0, template.opsForList().size("tutorials"));
        for (int i=0; i<testVal3.size(); i++)
            System.out.println("TestVal3 value: " + testVal3.get(i));

        template.opsForValue().set("number", "1234567890");
        System.out.println("HasKey Result Simple: " + template.hasKey("number"));
        System.out.println("7----------");
        System.out.println("Simple Input: " + template.opsForValue().get("number"));
        System.out.println("8----------");
        
        Map newMap = new HashMap();
        newMap.put("Brand 1", "Nike");
        newMap.put("Brand 2", "Adidas");
        newMap.put("Brand 3", "Under Armour");
        template.opsForHash().putAll("Sport", newMap);
        System.out.println("HasKey Result Hash Map: " + template.hasKey("Sport"));
        System.out.println("9----------");
        Map<Object, Object> hashVal2 = template.opsForHash().entries("Sport");
        for (Object key : hashVal2.keySet())
            System.out.println("Hash Input key: " + key + " TestVal2 value: " + hashVal2.get(key));

        
        List newList = new ArrayList();
        newList.add("Apple");
        newList.add("Samsung");
        newList.add("Xiaomi");
        template.opsForList().leftPushAll("Electronics", newList);
        System.out.println("HasKey Result List: " + template.hasKey("Electronics"));
        System.out.println("10----------");
        List<Object> listVal3 = template.opsForList().range("Electronics", 0, template.opsForList().size("Electronics"));
        for (int i=0; i<listVal3.size(); i++)
            System.out.println("TestVal3 value: " + listVal3.get(i));
        
        return new ResponseEntity<String>("{}", HttpStatus.OK);
    }
};
