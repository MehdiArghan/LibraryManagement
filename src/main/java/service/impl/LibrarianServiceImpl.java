package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Librarian;
import jakarta.persistence.EntityManager;
import repository.LibrarianRepository;
import service.LibrarianService;

public class LibrarianServiceImpl extends BaseServiceImpl<Long, Librarian, LibrarianRepository> implements LibrarianService {
    protected EntityManager entityManager;
    public LibrarianServiceImpl(EntityManager entityManager, LibrarianRepository repository) {
        super(entityManager, repository);
    }
}
