package com.metoeriver.library.book.entity;

import com.metoeriver.library.common.Tag;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Books {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String author;

    private String description;

    private LocalDate publishDate;

    @Enumerated(EnumType.ORDINAL)
    private Tag tag;
}
