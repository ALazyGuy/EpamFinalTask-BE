package test.com.ltp.web.model.entity;

import com.ltp.web.model.entity.PeopleEntity;
import com.ltp.web.model.entity.UserEntity;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class UserEntityFactoryTest {

    @Test
    public void createUserTest(){
        UserEntity expected = new UserEntity();
        expected.setCash(0);
        expected.setEmail("test");
        expected.setName("test");
        expected.setMiddleName("test");
        expected.setSurname("test");
        expected.setPassword("test");

        UserEntity actual = UserEntity.UserEntityFactory.createUser("test",
                "test",
                "test",
                "test",
                "test");

        AssertJUnit.assertEquals(expected, actual);
    }

    @Test
    public void createPeopleTest(){
        PeopleEntity expected = new PeopleEntity();
        expected.setFullName("test");
        expected.setStatus(true);
        expected.setPhotoName("test");

        PeopleEntity actual = PeopleEntity.PeopleEntityFactory.createPeople("test",
                0,
                true,
                "test");

        AssertJUnit.assertEquals(expected, actual);
    }

}
