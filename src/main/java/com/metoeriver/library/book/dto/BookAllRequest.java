package com.metoeriver.library.book.dto;

import com.metoeriver.library.book.entity.Tags;
import java.time.LocalDate;



public record BookAllRequest (
        Long id,
        String title,
        String author,
        String description,
        LocalDate publishedDate,
        Tags tag
){}

