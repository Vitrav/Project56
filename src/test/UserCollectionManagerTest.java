import model.collection.UserCollectionManager;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import user.Address;
import user.User;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserCollectionManagerTest {

    private final UserCollectionManager manager = new UserCollectionManager();
    private final String username = "UnitTest";
    private final String salt = BCrypt.gensalt();
    private final User testUser = new User(username, salt, BCrypt.hashpw("pass", salt), new Address("country", "city", "street", "12a", "1344ZP"), 20, new Date(), "email@test.com", false, true, false);

    @Test
    public void managerShouldNotHaveTestUser() throws Exception {
        assertEquals(manager.getUserDocument(username), null);
    }

    @Test
    public void managerShouldInsertAndDeleteUser() throws Exception {
        manager.insertUser(testUser);
        assertNotEquals(manager.getUserDocument(username), null);
        manager.deleteUser(testUser.getUsername());
        assertEquals(manager.getUserDocument(username), null);
    }

}