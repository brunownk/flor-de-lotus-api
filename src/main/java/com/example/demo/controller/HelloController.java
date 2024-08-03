package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Annotation to denote this class as a REST controller
@RestController

// Base URL for all endpoints in this controller
@RequestMapping("/api")
public class HelloController {

    // Mapping for GET requests to /api/hello
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
