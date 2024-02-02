package service;

import base.service.BaseService;
import entity.Librarian;

import java.util.Optional;

public interface LibrarianService extends BaseService<Long, Librarian> {
    Optional<Librarian> findByUserNameAndPassword(String userName, String password);

    boolean validate(Librarian librarian);
}
