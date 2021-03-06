package com.ltp.web.service.impl;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.mapper.PeopleMapper;
import com.ltp.web.model.dto.PeopleAddRequest;
import com.ltp.web.model.dto.PeopleEditRequest;
import com.ltp.web.model.entity.PeopleEntity;
import com.ltp.web.model.entity.UserEntity;
import com.ltp.web.model.entity.UserRole;
import com.ltp.web.repository.PeopleRepository;
import com.ltp.web.security.SecurityContext;
import com.ltp.web.service.PeopleService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void addPeople(PeopleAddRequest peopleAddRequest) throws SQLException, ConnectionPoolException {
        PeopleEntity peopleEntity = PeopleMapper.mapToPeople(peopleAddRequest);
        PeopleRepository.getInstance().save(peopleEntity);
    }

    @Override
    public void removePeople(Long id) throws SQLException, ConnectionPoolException {
        Optional<PeopleEntity> peopleEntity = PeopleRepository.getInstance().getById(id);

        if(peopleEntity.isEmpty()){
            return;
        }

        PeopleRepository.getInstance().remove(peopleEntity.get());
    }

    @Override
    public void changeStatus(Long id) throws SQLException, ConnectionPoolException {
        Optional<PeopleEntity> peopleEntity = PeopleRepository.getInstance().getById(id);

        if(peopleEntity.isEmpty()){
            return;
        }

        PeopleEntity people = peopleEntity.get();
        people.setStatus(true);
        PeopleRepository.getInstance().save(people);
    }

    @Override
    public Optional<PeopleEntity> getById(Long id) throws SQLException, ConnectionPoolException {
        return PeopleRepository.getInstance().getById(id);
    }

    @Override
    public void edit(PeopleEditRequest peopleEditRequest) throws SQLException, ConnectionPoolException {
        Optional<PeopleEntity> people = PeopleRepository.getInstance().getById(peopleEditRequest.getId());
        UserEntity current = SecurityContext.getInstance().getAuthenticated().get();

        if(current.getRole() == UserRole.ROLE_USER
                && (people.isEmpty() || people.get().getAuthorId() != current.getId())){
            return;
        }

        PeopleEntity peopleEntity = people.get();
        peopleEntity.setCash(peopleEditRequest.getCash());
        peopleEntity.setFullName(peopleEditRequest.getFullName());

        PeopleRepository.getInstance().save(peopleEntity);
    }

    public static PeopleServiceImpl getInstance(){
        return PeopleServiceImplHolder.INSTANCE;
    }

    private static final class PeopleServiceImplHolder{
        public static final PeopleServiceImpl INSTANCE = new PeopleServiceImpl();
    }

}
