package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Librarian;
import jakarta.persistence.EntityManager;
import repository.LibrarianRepository;

public class LibrarianRepositoryImpl extends BaseRepositoryImpl<Long, Librarian> implements LibrarianRepository {
    protected EntityManager entityManager;

    public LibrarianRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Class<Librarian> getEntityClass() {
        return Librarian.class;
    }
}
