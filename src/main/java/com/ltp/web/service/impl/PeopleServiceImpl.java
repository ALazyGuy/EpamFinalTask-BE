package com.ltp.web.service.impl;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.model.entity.PeopleEntity;
import com.ltp.web.repository.PeopleRepository;
import com.ltp.web.service.PeopleService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PeopleServiceImpl implements PeopleService {

    @Override
    public List<PeopleEntity> findSimilar(String fullName) throws SQLException, ConnectionPoolException {
        return PeopleRepository.getInstance().searchByFullName(fullName);
    }

    @Override
    public List<PeopleEntity> findAll() throws SQLException, ConnectionPoolException {
        return PeopleRepository.getInstance().getAll();
    }

    public static PeopleServiceImpl getInstance(){
        return PeopleServiceImplHolder.INSTANCE;
    }

    private static final class PeopleServiceImplHolder{
        public static final PeopleServiceImpl INSTANCE = new PeopleServiceImpl();
    }

}
