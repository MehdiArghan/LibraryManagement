package repository;

import base.repository.BaseRepository;
import entity.Librarian;

import java.util.Optional;

public interface LibrarianRepository extends BaseRepository<Long, Librarian> {
    Optional<Librarian> findByUserNameAndPassword(String userName, String password);
}
