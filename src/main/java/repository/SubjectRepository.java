package repository;

import base.repository.BaseRepository;
import entity.Subject;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SubjectRepository extends BaseRepository<Long, Subject> {
    Optional<Subject> findByTitle(String title);
}
