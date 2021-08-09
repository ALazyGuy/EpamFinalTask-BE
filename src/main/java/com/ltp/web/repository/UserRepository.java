package com.ltp.web.repository;

import com.ltp.web.connection.ConnectionPool;
import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.model.entity.UserEntity;
import com.ltp.web.model.entity.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements AbstractRepository<UserEntity>{

    private static final String DELETE_QUERY = "DELETE FROM `interpol`.`user` WHERE `id` = ?";
    private static final String GET_ALL_QUERY = "SELECT `id`, `email`, " +
            "`password`, `name`, `surname`, `middleName`, `cash`, `role` FROM `interpol`.`user`";
    private static final String GET_BY_ID_QUERY = "SELECT `id`, `email`, " +
            "`password`, `name`, `surname`, `middleName`, `cash`, `role` FROM `interpol`.`user` WHERE `id`=?";
    private static final String GET_BY_EMAIL_QUERY = "SELECT `id`, `email`, " +
            "`password`, `name`, `surname`, `middleName`, `cash`, `role` FROM `interpol`.`user` WHERE `email`=?";
    private static final String CREATE_QUERY = "INSERT INTO `interpol`.`user` (`password`, " +
            "`name`, `surname`, `middleName`, `cash`, `role`, `email`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE `interpol`.`user` SET `password` = ?, " +
            "`name` = ?, `surname` = ?, `middleName` = ?, `cash` = ?, `role` = ? " +
            "WHERE `id` = ?";

    private UserRepository(){}

    @Override
    public void remove(UserEntity userEntity) throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
        preparedStatement.setLong(1, userEntity.getId());
        preparedStatement.executeUpdate();
        ConnectionPool.getInstance().releaseConnection(connection);
    }

    @Override
    public List<UserEntity> getAll() throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(GET_ALL_QUERY);

        List<UserEntity> result = new LinkedList<>();

        while(resultSet.next()){
            result.add(mapToUserEntity(resultSet));
        }

        ConnectionPool.getInstance().releaseConnection(connection);

        return result;
    }

    @Override
    public Optional<UserEntity> getById(Long id) throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement(GET_BY_ID_QUERY);
        prepareStatement.setLong(1, id);
        ResultSet resultSet = prepareStatement.executeQuery();

        if(!resultSet.next()){
            ConnectionPool.getInstance().releaseConnection(connection);
            return Optional.empty();
        }

        ConnectionPool.getInstance().releaseConnection(connection);

        return Optional.of(mapToUserEntity(resultSet));
    }

    public Optional<UserEntity> getByEmail(String email) throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement(GET_BY_EMAIL_QUERY);
        prepareStatement.setString(1, email);
        ResultSet resultSet = prepareStatement.executeQuery();

        if(!resultSet.next()){
            ConnectionPool.getInstance().releaseConnection(connection);
            return Optional.empty();
        }

        ConnectionPool.getInstance().releaseConnection(connection);

        return Optional.of(mapToUserEntity(resultSet));
    }

    @Override
    public Optional<UserEntity> save(UserEntity userEntity) throws SQLException, ConnectionPoolException {
        boolean hasUser = getById(userEntity.getId()).isPresent() || getByEmail(userEntity.getEmail()).isPresent();
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(hasUser ? UPDATE_QUERY : CREATE_QUERY);
        preparedStatement.setString(1, userEntity.getPassword());
        preparedStatement.setString(2, userEntity.getName());
        preparedStatement.setString(3, userEntity.getSurname());
        preparedStatement.setString(4, userEntity.getMiddleName());
        preparedStatement.setLong(5, userEntity.getCash());
        preparedStatement.setString(6, userEntity.getRole().name());

        if(hasUser){
            preparedStatement.setLong(7, userEntity.getId());
        }else{
            preparedStatement.setString(7, userEntity.getEmail());
        }

        preparedStatement.executeUpdate();

        ConnectionPool.getInstance().releaseConnection(connection);

        return (hasUser ? Optional.of(userEntity) : getById(userEntity.getId()));
    }

    private UserEntity mapToUserEntity(ResultSet resultSet) throws SQLException {
        String email = resultSet.getString("email"),
                password = resultSet.getString("password"),
                name = resultSet.getString("name"),
                surname = resultSet.getString("surname"),
                middleName = resultSet.getString("middleName");
        Long id = resultSet.getLong("id"),
                cash = resultSet.getLong("cash");
        UserRole role = UserRole.valueOf(resultSet.getString("role"));

        return new UserEntity(id, email, password, name, surname, middleName, cash, role);
    }

    public static UserRepository getInstance(){
        return UserRepositoryHolder.INSTANCE;
    }

    private static final class UserRepositoryHolder{
        public static final UserRepository INSTANCE = new UserRepository();
    }

}
