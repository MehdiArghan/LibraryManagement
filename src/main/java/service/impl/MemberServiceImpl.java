package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Member;
import jakarta.persistence.EntityManager;
import repository.MemberRepository;
import service.MemberService;
import util.checkValidation;

import java.util.Optional;

public class MemberServiceImpl extends BaseServiceImpl<Long, Member, MemberRepository> implements MemberService {
    protected EntityManager entityManager;

    public MemberServiceImpl(EntityManager entityManager, MemberRepository repository) {
        super(entityManager, repository);
        this.entityManager = entityManager;
    }

    public void save(Member member) {
        try {
            entityManager.getTransaction().begin();
            repository.save(member);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Optional<Member> findByUserNameAndPassword(String userName, String password) {
        return repository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public boolean validate(Member member) {
        boolean valid = checkValidation.isValid(member);
        if (valid) {
            save(member);
            return true;
        } else {
            return false;
        }
    }
}
