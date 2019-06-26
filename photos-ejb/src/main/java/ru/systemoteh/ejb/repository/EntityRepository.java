package ru.systemoteh.ejb.repository;

import java.util.Optional;

public interface EntityRepository<T, ID> {

    Optional<T> findById(ID id);

    void create(T entity);

    void update(T entity);

    void delete(T entity);

    void flush();

    T getProxyInstance(ID id);
}
