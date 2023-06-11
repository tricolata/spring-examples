package com.springexamples.kafkaexample.controller;

import com.springexamples.kafkaexample.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping
    public ResponseEntity<?> postMessage(@RequestBody String message) {
        service.publishStringMessage(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
