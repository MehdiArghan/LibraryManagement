package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Librarian;
import jakarta.persistence.EntityManager;
import repository.LibrarianRepository;
import service.LibrarianService;
import util.checkValidation;

import java.util.Optional;

public class LibrarianServiceImpl extends BaseServiceImpl<Long, Librarian, LibrarianRepository> implements LibrarianService {
    protected EntityManager entityManager;

    public LibrarianServiceImpl(EntityManager entityManager, LibrarianRepository repository) {
        super(entityManager, repository);
        this.entityManager = entityManager;
    }

    public void save(Librarian librarian) {
        try {
            entityManager.getTransaction().begin();
            repository.save(librarian);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Optional<Librarian> findByUserNameAndPassword(String userName, String password) {
        return repository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public boolean validate(Librarian librarian) {
        boolean valid = checkValidation.isValid(librarian);
        if (valid) {
            save(librarian);
            return true;
        } else {
            return false;
        }
    }
}
