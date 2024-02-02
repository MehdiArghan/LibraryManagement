package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Member;
import jakarta.persistence.EntityManager;
import repository.MemberRepository;

import java.util.Optional;

public class MemberRepositoryImpl extends BaseRepositoryImpl<Long, Member> implements MemberRepository {
    protected EntityManager entityManager;

    public MemberRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Class<Member> getEntityClass() {
        return Member.class;
    }

    @Override
    public Optional<Member> findByUserNameAndPassword(String userName, String password) {
        return Optional.of(
                entityManager.createQuery("from Member where userName=:USERNAME and password=:PASSWORD", Member.class)
                        .setParameter("USERNAME", userName)
                        .setParameter("PASSWORD", password)
                        .getSingleResult()
        );
    }
}
