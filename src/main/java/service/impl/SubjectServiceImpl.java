package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Subject;
import jakarta.persistence.EntityManager;
import repository.SubjectRepository;
import service.SubjectService;

public class SubjectServiceImpl extends BaseServiceImpl<Long, Subject, SubjectRepository> implements SubjectService {
    protected EntityManager entityManager;
    public SubjectServiceImpl(EntityManager entityManager, SubjectRepository repository) {
        super(entityManager, repository);
    }
}
