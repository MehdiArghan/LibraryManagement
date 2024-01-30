package entity;

import base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public class Person extends BaseEntity<Long> {
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String userName;
    @NotNull
    String password;
}
