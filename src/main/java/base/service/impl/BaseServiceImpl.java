package base.service.impl;

import base.entity.BaseEntity;
import base.repository.BaseRepository;
import base.service.BaseService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.TransactionalException;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BaseServiceImpl<ID extends Serializable, Entity extends BaseEntity<ID>,
        Repository extends BaseRepository<ID, Entity>> implements BaseService<ID, Entity> {
    protected EntityManager entityManager;
    protected Repository repository;

    @Override
    public void save(Entity entity) {
        try {
            entityManager.getTransaction().begin();
            repository.save(entity);
            entityManager.getTransaction().commit();
        } catch (TransactionalException e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void remove(Entity entity) {
        try {
            entityManager.getTransaction().begin();
            repository.remove(entity);
            entityManager.getTransaction().commit();
        } catch (TransactionalException e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void update(Entity entity) {
        try {
            entityManager.getTransaction().begin();
            repository.update(entity);
            entityManager.getTransaction().commit();
        } catch (TransactionalException e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Optional<Entity> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<Entity> loadAll() {
        return repository.loadAll();
    }
}
