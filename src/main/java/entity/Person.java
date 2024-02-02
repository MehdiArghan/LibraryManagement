package entity;

import base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Person extends BaseEntity<Long> {
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @Column(unique = true)
    String userName;
    @NotNull
    String password;
}
