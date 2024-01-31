package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Member;
import jakarta.persistence.EntityManager;
import repository.MemberRepository;
import service.MemberService;

public class MemberServiceImpl extends BaseServiceImpl<Long, Member, MemberRepository> implements MemberService {
    protected EntityManager entityManager;
    public MemberServiceImpl(EntityManager entityManager, MemberRepository repository) {
        super(entityManager, repository);
    }
}
