package book.entity;

import common.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    private Date publishDate;

    @Enumerated(EnumType.ORDINAL)
    private Tag tag;
}
