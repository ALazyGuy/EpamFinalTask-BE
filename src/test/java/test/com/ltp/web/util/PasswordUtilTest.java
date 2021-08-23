package test.com.ltp.web.util;

import com.ltp.web.util.PasswordUtil;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class PasswordUtilTest {

    @Test
    public void validateTest(){
        String password = "TEST_PASS";
        String hash1 = PasswordUtil.getInstance().encode(password);
        boolean result = PasswordUtil.getInstance().validate(password, hash1);
        AssertJUnit.assertTrue(result);
    }

}
