package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Librarian;
import jakarta.persistence.EntityManager;
import repository.LibrarianRepository;

import java.util.Optional;

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

    @Override
    public Optional<Librarian> findByUserNameAndPassword(String userName, String password) {
        return Optional.of(
                entityManager.createQuery("from Librarian where userName=:USERNAME and password=:PASSWORD", Librarian.class)
                        .setParameter("USERNAME", userName)
                        .setParameter("PASSWORD", password)
                        .getSingleResult()
        );
    }
}
