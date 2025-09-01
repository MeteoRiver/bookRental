package com.metoeriver.library.book.dto;

import java.time.LocalDate;
import java.util.List;


public record BookAllRequest (
        Long id,
        String title,
        String author,
        String description,
        LocalDate publishedDate,
        List<String> tag
){}

