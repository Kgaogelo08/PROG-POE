package quickchat;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegistrationTest {
    @Test public void testUsernameCorrect() { assertTrue(Registration.checkUserName("kyl_1")); }
    @Test public void testUsernameIncorrect() { assertFalse(Registration.checkUserName("kyle!!!!")); }
    @Test public void testPasswordCorrect() { assertTrue(Registration.checkPasswordComplexity("Ch&&sec@ke99!")); }
    @Test public void testPasswordIncorrect() { assertFalse(Registration.checkPasswordComplexity("password")); }
    @Test public void testCellCorrect() { assertTrue(Registration.checkCellPhoneNumber("+27834557896")); }
    @Test public void testCellIncorrect() { assertFalse(Registration.checkCellPhoneNumber("08966553")); }
    @Test public void testLoginSuccess() {
        Login l = new Login(); l.storeUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        assertTrue(l.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    @Test public void testLoginFail() {
        Login l = new Login(); l.storeUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        assertFalse(l.loginUser("kyl_1", "wrong"));
    }
}