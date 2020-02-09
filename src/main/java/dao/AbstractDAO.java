package dao;

import java.util.List;

public interface AbstractDAO<K, T> {
    List<T> findAll();

    T findById(K id);

    boolean delete(K id);

  //  boolean delete(T entity);

    boolean create(T entity);

    T update(T entity);

}
