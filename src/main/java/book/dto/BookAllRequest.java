package book.dto;

import common.Tag;
import java.time.LocalDate;



public record BookAllRequest (
        Long id,
        String title,
        String author,
        String description,
        LocalDate publishedDate,
        Tag tag
){}

