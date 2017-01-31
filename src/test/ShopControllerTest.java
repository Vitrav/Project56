import controller.ShopController;
import model.collection.UserCollectionManager;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;

//Test the cart logic when not logged in
public class ShopControllerTest {

    private final String testUser = "user";

    @Test
    public void removeGameFromUser() throws Exception {
        ShopController.getUserItems().clear();
        assertFalse(ShopController.getUserItems().containsKey(testUser));
        ShopController.getUserItems().put(testUser, new LinkedList<>(Arrays.asList(new UserCollectionManager().createCartItemDocument(1))));
        assertTrue(ShopController.getUserItems().containsKey(testUser));
        assertTrue(ShopController.userHasGame(testUser, 1));
        assertTrue(ShopController.getUserItems().get(testUser).get(0).getInteger("id") == 1);
        ShopController.removeUserItem(testUser, 1);
        assertFalse(ShopController.userHasGame(testUser, 1));
    }

    @Test
    public void incGameAmount() throws Exception {
        ShopController.getUserItems().put(testUser, new LinkedList<>(Arrays.asList(new UserCollectionManager().createCartItemDocument(1))));
        assertTrue(ShopController.getUserItems().get(testUser).get(0).getInteger("amount") == 1);
        ShopController.incGameAmount(testUser, 1);
        assertTrue(ShopController.getUserItems().get(testUser).get(0).getInteger("amount") == 2);
        ShopController.incGameAmount(testUser, 2);
        assertTrue(ShopController.getUserItems().get(testUser).get(0).getInteger("amount") == 2);
    }


}