package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Subject;
import jakarta.persistence.EntityManager;
import repository.SubjectRepository;

import java.util.Optional;

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

    @Override
    public Optional<Subject> findByTitle(String title) {
        return Optional.ofNullable(
                entityManager.createQuery("from Subject where title=:TITLE", Subject.class)
                        .setParameter("TITLE", title)
                        .getSingleResult()
        );
    }
}