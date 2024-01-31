package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Book;
import jakarta.persistence.EntityManager;
import repository.BookRepository;

public class BookRepositoryImpl extends BaseRepositoryImpl<Long, Book> implements BookRepository {
    protected EntityManager entityManager;

    public BookRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Class<Book> getEntityClass() {
        return Book.class;
    }
}
