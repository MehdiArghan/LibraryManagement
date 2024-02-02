package service;

import base.service.BaseService;
import entity.Member;

import java.util.Optional;

public interface MemberService extends BaseService<Long, Member> {
    Optional<Member> findByUserNameAndPassword(String userName, String password);

    boolean validate(Member member);
}
