package com.springexamples.kafkaexample.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Book {

    private Long id;
    private String name;
    private String author;
    private Integer yearPublished;
}
