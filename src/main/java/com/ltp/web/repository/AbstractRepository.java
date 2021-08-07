package com.ltp.web.repository;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.model.entity.AbstractEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T extends AbstractEntity> {
    void remove(T t) throws ConnectionPoolException, SQLException;
    List<T> getAll() throws ConnectionPoolException, SQLException;
    Optional<T> getById(Long id) throws ConnectionPoolException, SQLException;
    Optional<T> save(T t) throws SQLException, ConnectionPoolException;
}
