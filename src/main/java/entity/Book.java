package entity;

import base.entity.BaseEntity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book extends BaseEntity<Long> {
    String title;
    String author;
    @ManyToOne
    Subject subject;
}
