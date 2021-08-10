package com.ltp.web.service;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.model.entity.PeopleEntity;

import java.sql.SQLException;
import java.util.List;

public interface PeopleService {
    List<PeopleEntity> findSimilar(String fullName) throws SQLException, ConnectionPoolException;
    List<PeopleEntity> findAll() throws SQLException, ConnectionPoolException;
    //TODO Add creation and updating
}
