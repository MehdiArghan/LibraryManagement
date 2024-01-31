package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Member;
import jakarta.persistence.EntityManager;
import repository.MemberRepository;

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
}
