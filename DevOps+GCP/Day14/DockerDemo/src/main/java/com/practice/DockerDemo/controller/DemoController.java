package com.practice.DockerDemo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class DemoController {
    @GetMapping
    ResponseEntity<String> demo(){
        return ResponseEntity.ok("Hello World!");
    }
}
