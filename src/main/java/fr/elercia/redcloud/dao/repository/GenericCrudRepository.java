package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.entity.DBEntity;

import java.util.List;

public interface GenericCrudRepository<T extends DBEntity> {

    T add(T entity);

    void delete(T entity);

    void update(T entity);

    List<T> findAll();

    T findById(Long id);
}
