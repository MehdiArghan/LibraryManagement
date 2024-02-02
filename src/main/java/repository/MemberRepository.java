package repository;

import base.repository.BaseRepository;
import entity.Member;

import java.util.Optional;

public interface MemberRepository extends BaseRepository<Long, Member> {
    Optional<Member> findByUserNameAndPassword(String userName, String password);
}
