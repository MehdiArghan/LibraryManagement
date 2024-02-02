package service;

import base.service.BaseService;
import entity.Subject;

import java.util.Optional;

public interface SubjectService extends BaseService<Long, Subject> {
    Optional<Subject> findByTitle(String title);
}
