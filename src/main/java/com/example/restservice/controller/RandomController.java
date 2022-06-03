package com.example.restservice.controller;

import com.example.restservice.CounterThread;
import com.example.restservice.MyLogger;
import com.example.restservice.cache.Cache;
import com.example.restservice.cache.CacheKey;
import com.example.restservice.exception.WrongInputFormat;
import com.example.restservice.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RandomController {

    @Autowired
    public RandomController(Cache cache) {
        this.cache = cache;
    }

    private final Cache cache;
    private static final String template = "Your number is: %s, number that is %s is %d";
    private final AtomicLong idCounter = new AtomicLong();

    @GetMapping("/task")
    public Random random(@RequestParam(value = "number", required = false) String number, @RequestParam(value = "side", required = false) String side) throws Exception {
        new CounterThread().start();
        MyLogger.info("Request GET /task with params: \"" + number + "\", \"" + side + "\"");
        int num;
        try {
            num = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            MyLogger.error(e.getMessage());
            throw new WrongInputFormat(e.getMessage());
        }
        CacheKey cacheKey = new CacheKey(num, side);
        int max = 1_000_000;
        if (num > max) {
            MyLogger.error("Error: Too big num");
           // throw new WrongInputFormat("Too big num");
            throw new Exception("Too big num");

        }
        if (num <= 0) {
            MyLogger.error("Error: Number should be positive");
            throw new WrongInputFormat("Number should be positive");
        }

        if (cache.isCached(cacheKey)) {
            Integer cachedNum = cache.get(cacheKey);
            return new Random(idCounter.incrementAndGet(), String.format(template, number, side, cachedNum));
        }

        int number_to_output = 10;
        if (!side.equals("<") && !side.equals(">")) {
            MyLogger.error("No operation provided");
            throw new WrongInputFormat("No operation provided");
        }
        if (side.equals(">")) {
            max -= num;
            number_to_output = (int) (Math.random() * (max + 1)) + num;
        } else {
            number_to_output = (int) (Math.random() * (num + 1));
        }
        cache.add(new CacheKey(num, side), number_to_output);
        return new Random(idCounter.incrementAndGet(), String.format(template, number, side, number_to_output));
    }

}


