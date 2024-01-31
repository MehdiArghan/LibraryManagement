package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Person;
import jakarta.persistence.EntityManager;
import repository.PersonRepository;
import service.PersonService;

public class PersonServiceImpl extends BaseServiceImpl<Long, Person, PersonRepository> implements PersonService {
    protected EntityManager entityManager;
    public PersonServiceImpl(EntityManager entityManager, PersonRepository repository) {
        super(entityManager, repository);
    }
}
