package com.ltp.web.repository;

import com.ltp.web.connection.ConnectionPool;
import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.model.entity.PeopleEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PeopleRepository implements AbstractRepository<PeopleEntity> {

    private static final String DELETE_QUERY = "DELETE FROM `interpol`.`people` WHERE `id`=?";
    private static final String GET_ALL_QUERY = "SELECT `id`, `fullName`, `cash`, `status`, `photoName` from `interpol`.`people`";
    private static final String GET_BY_ID_QUERY = "SELECT `id`, `fullName`, `cash`, `status`, `photoName` from `interpol`.`people` WHERE `id`=?";
    private static final String CREATE_QUERY = "INSERT INTO `interpol`.`people` (`fullName`, `cash`, `status`, `photoName`) VALUES" +
            " (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = " UPDATE `interpol`.`people` SET `fullName`=?, `cash`=?, `status`=?, `photoName`=? WHERE `id`=?";
    private static final String SEARCH_QUERY = "SELECT `id`, `fullName`, `cash`, `status`, `photoName` FROM `interpol`.`people` " +
            "WHERE `fullName` LIKE ?";

    @Override
    public void remove(PeopleEntity peopleEntity) throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
        preparedStatement.setLong(1, peopleEntity.getId());
        preparedStatement.executeUpdate();
        ConnectionPool.getInstance().releaseConnection(connection);
    }

    @Override
    public List<PeopleEntity> getAll() throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(GET_ALL_QUERY);

        List<PeopleEntity> result = new LinkedList<>();

        while(resultSet.next()){
            result.add(mapToPeople(resultSet));
        }

        ConnectionPool.getInstance().releaseConnection(connection);

        return result;
    }

    @Override
    public Optional<PeopleEntity> getById(Long id) throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement(GET_BY_ID_QUERY);
        prepareStatement.setLong(1, id);
        ResultSet resultSet = prepareStatement.executeQuery();

        if(!resultSet.next()){
            ConnectionPool.getInstance().releaseConnection(connection);
            return Optional.empty();
        }

        ConnectionPool.getInstance().releaseConnection(connection);

        return Optional.of(mapToPeople(resultSet));
    }

    @Override
    public Optional<PeopleEntity> save(PeopleEntity peopleEntity) throws SQLException, ConnectionPoolException {
        boolean hasPeople = getById(peopleEntity.getId()).isPresent();
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(hasPeople ? UPDATE_QUERY : CREATE_QUERY);
        preparedStatement.setString(1, peopleEntity.getFullName());
        preparedStatement.setLong(2, peopleEntity.getCash());
        preparedStatement.setBoolean(3, peopleEntity.isStatus());
        preparedStatement.setString(4, peopleEntity.getPhotoName());

        if(hasPeople){
            preparedStatement.setLong(5, peopleEntity.getId());
        }

        preparedStatement.executeUpdate();

        ConnectionPool.getInstance().releaseConnection(connection);

        return (hasPeople ? Optional.of(peopleEntity) : getById(peopleEntity.getId()));
    }

    public List<PeopleEntity> searchByFullName(String fullName) throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_QUERY);
        preparedStatement.setString(1, String.format("%%%s%%", fullName));

        ResultSet resultSet = preparedStatement.executeQuery();

        List<PeopleEntity> result = new LinkedList<>();

        while(resultSet.next()){
            result.add(mapToPeople(resultSet));
        }

        ConnectionPool.getInstance().releaseConnection(connection);

        return result;
    }

    private PeopleEntity mapToPeople(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id"),
                cash = resultSet.getLong("cash");
        String fullName = resultSet.getString("fullName"),
                photoName = resultSet.getString("photoName");
        boolean status = resultSet.getBoolean("status");

        return new PeopleEntity(id, fullName, cash, status, photoName);
    }

    public static PeopleRepository getInstance(){
        return PeopleRepositoryHolder.INSTANCE;
    }

    private static final class PeopleRepositoryHolder{
        public static final PeopleRepository INSTANCE = new PeopleRepository();
    }

}
