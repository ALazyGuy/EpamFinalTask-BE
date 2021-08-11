package test.com.ltp.web.service.impl;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.mapper.PeopleMapper;
import com.ltp.web.mapper.UserMapper;
import com.ltp.web.model.dto.PeopleAddRequest;
import com.ltp.web.model.dto.RegistrationRequest;
import com.ltp.web.model.entity.PeopleEntity;
import com.ltp.web.model.entity.UserEntity;
import com.ltp.web.repository.PeopleRepository;
import com.ltp.web.repository.UserRepository;
import com.ltp.web.service.impl.PeopleServiceImpl;
import com.ltp.web.service.impl.UserServiceImpl;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PeopleServiceImplTest {

    private PeopleEntity expected;
    private PeopleAddRequest peopleAddRequest;

    @BeforeMethod
    public void init(){
        peopleAddRequest = new PeopleAddRequest("NAME1", 100L, "PHOTO");
        expected = PeopleMapper.mapToPeople(peopleAddRequest);
    }

    @Test
    public void addPeopleTest() throws SQLException, ConnectionPoolException {
        PeopleServiceImpl.getInstance().addPeople(peopleAddRequest);
        List<PeopleEntity> peoples = PeopleRepository.getInstance().getAll()
                .stream()
                .filter(peopleEntity ->
                        peopleEntity
                                .getFullName()
                                .equals(expected.getFullName()))
                .collect(Collectors.toList());

        if(peoples.size() == 0){
            AssertJUnit.fail();
        }

        PeopleEntity actual = peoples.get(0);

        expected.setId(actual.getId());

        PeopleRepository.getInstance().remove(actual);

        AssertJUnit.assertEquals(expected, actual);
    }

    @Test
    public void findSimilarTest() throws SQLException, ConnectionPoolException {
        PeopleServiceImpl.getInstance().addPeople(peopleAddRequest);
        List<PeopleEntity> peoples = PeopleServiceImpl.getInstance().findSimilar("AME");

        if(peoples.size() == 0){
            AssertJUnit.fail();
        }

        PeopleEntity actual = peoples.get(0);

        expected.setId(actual.getId());

        PeopleRepository.getInstance().remove(actual);

        AssertJUnit.assertEquals(expected, actual);
    }

}
