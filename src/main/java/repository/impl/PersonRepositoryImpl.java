package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Person;
import jakarta.persistence.EntityManager;
import repository.PersonRepository;

public class PersonRepositoryImpl extends BaseRepositoryImpl<Long, Person> implements PersonRepository {
    protected EntityManager entityManager;

    public PersonRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Class<Person> getEntityClass() {
        return Person.class;
    }
}
