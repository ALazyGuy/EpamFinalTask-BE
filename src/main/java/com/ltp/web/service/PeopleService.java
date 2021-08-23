package com.ltp.web.service;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.model.dto.PeopleAddRequest;
import com.ltp.web.model.dto.PeopleEditRequest;
import com.ltp.web.model.entity.PeopleEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PeopleService {
    List<PeopleEntity> findSimilar(String fullName) throws SQLException, ConnectionPoolException;
    List<PeopleEntity> findAll() throws SQLException, ConnectionPoolException;
    Optional<PeopleEntity> getById(Long id) throws SQLException, ConnectionPoolException;
    void addPeople(PeopleAddRequest peopleAddRequest) throws SQLException, ConnectionPoolException;
    void removePeople(Long id) throws SQLException, ConnectionPoolException;
    void changeStatus(Long id) throws SQLException, ConnectionPoolException;
    void edit(PeopleEditRequest peopleEditRequest) throws SQLException, ConnectionPoolException;
}
