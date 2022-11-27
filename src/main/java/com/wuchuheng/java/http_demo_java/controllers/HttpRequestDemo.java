package com.wuchuheng.java.http_demo_java.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Slf4j
public class HttpRequestDemo {
    /**
     * Get 请求/
     */
    @GetMapping("/")
    public ResponseEntity<String> getRequest(
            @RequestHeader Map<String, String> headers,
            @RequestParam Map<String, String> params
    ) {
        headers.forEach((key, value) -> {
            log.info("header value: key: {}, value: {}", key, value);
        });
        params.forEach((key, value) -> {
            log.info("params: key: {}, value: {}", key, value);
        });

        return ResponseEntity
                .ok()
                .header("custom-field", "custom-value")
                .body( "Hello world");
    }
}
