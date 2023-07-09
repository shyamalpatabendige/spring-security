package com.shyamalmadura.spring.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public String publicPage() {
        return "Hello, Welcome everyone to the Application";
    }

    @GetMapping("/secured")
    public String securedPage() {
        return "Hello, you are allowed to access this page";
    }
}
