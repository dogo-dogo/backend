package com.example.tooktook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class httpTestController {

    @GetMapping("/https")
    public ResponseEntity<String> getOneHello(){
        return ResponseEntity.ok("Hello");
    }
    @GetMapping("/docker")
    public ResponseEntity<String> getOnedocker(){
        return ResponseEntity.ok("docker");
    }
}
