package com.ltp.web.repository;

import com.ltp.web.model.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T extends AbstractEntity> {
    Optional<T> remove(T t);
    List<T> getAll();
    Optional<T> getById(Long id);
    void save(T t);
}
