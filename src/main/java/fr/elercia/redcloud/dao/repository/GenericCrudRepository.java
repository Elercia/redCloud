package fr.elercia.redcloud.dao.repository;


import org.jooq.Record;

import java.util.List;

public interface GenericCrudRepository<T extends Record> {

    T add(T entity);

    void delete(T entity);

    void update(T entity);

    List<T> findAll();

    T findById(Long id);
}
