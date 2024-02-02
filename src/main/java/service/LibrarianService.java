package service;

import base.service.BaseService;
import entity.Librarian;
import entity.Member;

public interface LibrarianService extends BaseService<Long, Librarian> {
    boolean validate(Librarian librarian);
}
