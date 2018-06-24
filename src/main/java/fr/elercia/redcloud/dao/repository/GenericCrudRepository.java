package fr.elercia.redcloud.dao.repository;


import java.util.List;

public interface GenericCrudRepository<T> {

    T add(T entity);

    void delete(T entity);

    void update(T entity);

    List<T> findAll();

    T findById(int id);
}
