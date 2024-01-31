package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Book;
import jakarta.persistence.EntityManager;
import repository.BookRepository;
import service.BookService;

public class BookServiceImpl extends BaseServiceImpl<Long, Book, BookRepository> implements BookService {
    protected EntityManager entityManager;

    public BookServiceImpl(EntityManager entityManager, BookRepository repository) {
        super(entityManager, repository);
        this.entityManager = entityManager;
    }
}
