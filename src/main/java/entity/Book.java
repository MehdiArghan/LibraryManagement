package entity;

import base.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Book extends BaseEntity<Long> {
    String title;
    String author;
    @ManyToOne
    Subject subject;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
