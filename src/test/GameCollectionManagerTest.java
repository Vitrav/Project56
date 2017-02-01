import model.collection.GameCollectionManager;
import org.junit.Test;
import parser.Game;

import java.util.Date;

import static org.junit.Assert.*;

public class GameCollectionManagerTest {

    //Given, when, then
    private final GameCollectionManager manager = new GameCollectionManager();
    private final Game testGame = new Game(-2, "game", "publisher", 12.11, 18, "PC", "Shooter", 20, new Date(), "desc", "image.png");

    @Test
    public void databaseHasGame() throws Exception {
        assertFalse(manager.databaseHasGame(-2));
    }

    @Test
    public void insertRemoveGame() throws Exception {
        manager.insertNewGame(testGame);
        assertTrue(manager.databaseHasGame(-2));
        manager.removeGame(-2);
        assertFalse(manager.databaseHasGame(-2));
    }

    @Test
    public void getGameDocument() throws Exception {
        if (!manager.databaseHasGame(-2))
            manager.insertNewGame(testGame);
        assertTrue(manager.databaseHasGame(-2));
        assertEquals(manager.createGameDocument(testGame), manager.getGameDocument(-2));
        manager.removeGame(-2);
    }

}