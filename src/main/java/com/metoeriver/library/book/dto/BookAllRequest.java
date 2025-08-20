package com.metoeriver.library.book.dto;

import com.metoeriver.library.common.Tag;
import java.time.LocalDate;



public record BookAllRequest (
        Long id,
        String title,
        String author,
        String description,
        LocalDate publishedDate,
        Tag tag
){}

