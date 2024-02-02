package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member extends Person {
    @ManyToMany
    @JoinTable(
            name = "bookReserve",
            inverseJoinColumns = @JoinColumn(name = "book_id"),
            joinColumns = @JoinColumn(name = "member_id"))
    Set<Book> bookReserveList;

    @ManyToMany
    @JoinTable(
            name = "bookBorrowed",
            inverseJoinColumns = @JoinColumn(name = "book_id"),
            joinColumns = @JoinColumn(name = "member_id"))
    Set<Book> bookBorrowedList;

    @ManyToMany
    @JoinTable(
            name = "bookRenewalDeadline",
            inverseJoinColumns = @JoinColumn(name = "book_id"),
            joinColumns = @JoinColumn(name = "member_id"))
    Set<Book> bookRenewalDeadlineList;

    @ManyToMany
    @JoinTable(
            name = "historyOFBorrowedBook",
            inverseJoinColumns = @JoinColumn(name = "book_id"),
            joinColumns = @JoinColumn(name = "member_id"))
    Set<Book> historyOFBorrowedBookList;

    public Member(@NotNull String firstName, @NotNull String lastName, @NotNull String userName, @NotNull String password) {
        super(firstName, lastName, userName, password);
    }
}
