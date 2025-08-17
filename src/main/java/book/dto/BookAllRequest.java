package book.dto;

import common.Tag;
import java.util.Date;



public record BookAllRequest (
        Long id,
        String title,
        String author,
        String description,
        Date publishedDate,
        Tag tag
){}

