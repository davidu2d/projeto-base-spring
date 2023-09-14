package com.u2d.projeto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin("*")
public class TestController {

    @GetMapping
    public ResponseEntity<?> findMsg(){
        return ResponseEntity.ok("Teste funcional da api V100");
    }
}
