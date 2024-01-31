package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Subject;
import jakarta.persistence.EntityManager;
import repository.SubjectRepository;

public class SubjectRepositoryImpl extends BaseRepositoryImpl<Long, Subject> implements SubjectRepository {
    protected EntityManager entityManager;
    public SubjectRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }
    @Override
    public Class<Subject> getEntityClass() {
        return Subject.class;
    }
}
