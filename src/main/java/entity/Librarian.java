package entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Librarian extends Person {
    String role;
    public Librarian(@NotNull String firstName, @NotNull String lastName, @NotNull String userName, @NotNull String password, String role) {
        super(firstName, lastName, userName, password);
        this.role = role;
    }
}