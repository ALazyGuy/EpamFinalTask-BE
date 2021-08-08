package test.com.ltp.web.service.impl;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.mapper.UserMapper;
import com.ltp.web.model.dto.RegistrationRequest;
import com.ltp.web.model.entity.UserEntity;
import com.ltp.web.repository.UserRepository;
import com.ltp.web.service.UserService;
import com.ltp.web.service.impl.UserServiceImpl;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Optional;

public class UserServiceImplTest {

    private UserEntity expected;
    private RegistrationRequest registrationRequest;

    @BeforeMethod
    public void init(){
        registrationRequest = new RegistrationRequest("test",
                "test",
                "test",
                "test",
                "test");
        expected = UserMapper.mapToUser(registrationRequest);
    }

    @Test
    public void addUserTest() throws SQLException, ConnectionPoolException {
        UserServiceImpl.getInstance().addUser(registrationRequest);
        Optional<UserEntity> actual = UserRepository.getInstance().getByEmail("test");

        if(actual.isEmpty()){
            AssertJUnit.fail();
        }

        expected.setId(actual.get().getId());

        UserRepository.getInstance().remove(actual.get());

        AssertJUnit.assertEquals(expected, actual.get());
    }

    @Test
    public void getUserByEmailTest() throws SQLException, ConnectionPoolException {
        UserServiceImpl.getInstance().addUser(registrationRequest);
        Optional<UserEntity> actual = UserServiceImpl.getInstance().getUserByEmail(expected.getEmail());

        if(actual.isEmpty()){
            AssertJUnit.fail();
        }

        expected.setId(actual.get().getId());

        UserRepository.getInstance().remove(actual.get());

        AssertJUnit.assertEquals(expected, actual.get());
    }

}
