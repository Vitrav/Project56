package parser;

import model.collection.GameCollectionManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class GameParserTest {

//    private GameParser gameParser;
//
//    @Before
//    public void setUp() throws Exception {
//        gameParser = new GameParser();
//    }
//
//    @Test
//    public void linesEmpty() throws Exception {
//        assertTrue(gameParser.getLines().isEmpty());
//    }
//
//    @Test
//    public void linesFilled() throws Exception {
//        gameParser.fillLines();
//        assertFalse(gameParser.getLines().isEmpty());
//    }
//
//    @Test
//    public void gamesEmpty() throws Exception {
//        assertTrue(gameParser.getGames().isEmpty());
//    }
//
//    @Test
//    public void gamesFilled() throws Exception {
//        gameParser.addGames();
//        assertFalse(gameParser.getGames().isEmpty());
//    }
//
//    @Test
//    public void reverseDate() throws Exception {
//        assertEquals("2016/11/15", gameParser.reverseDate("15-11-2016"));
//    }
//
//    @Test
//    public void removeFromLine() throws Exception {
//        String line = "0;Battlefield 1;Electronic Arts;49.99;18;PC;FPS;21-10-2016;Ervaar het begin van totale oorlog in Battlefield 1. Baan je een weg door epische gevechten, varierend van krappe belegerde Franse steden tot zwaar verdedigde bergforten in de Italiaanse Alpen, en aanschouw waanzinnige veldslagen in de woestijn van Arabie. Ontdek een nieuwe wereld in oorlog via een avontuurlijke campagne, of doe mee aan epische multiplayer gevechten met maximaal 64 spelers. Stem je tactiek af op de adembenemende omgevingen en totale verwoesting. Strijd als infanterie of bestuur ongelooflijke voertuigen te land, ter zee en in de lucht (varierend van tanks en motoren op de grond tot dubbeldekkers en gigantische oorlogsschepen), en stem je speelstijl af op de meest dynamische gevechten in de geschiedenis van Battlefield.; http://media-www-battlefieldwebcore.spark.ea.com/content/battlefield-portal/nl_NL/_global_/_jcr_content/ccm/componentwrapper_0/components/opengraph/ogImage.img.jpg";
//        assertEquals("Battlefield 1;Electronic Arts;49.99;18;PC;FPS;21-10-2016;Ervaar het begin van totale oorlog in Battlefield 1. Baan je een weg door epische gevechten, varierend van krappe belegerde Franse steden tot zwaar verdedigde bergforten in de Italiaanse Alpen, en aanschouw waanzinnige veldslagen in de woestijn van Arabie. Ontdek een nieuwe wereld in oorlog via een avontuurlijke campagne, of doe mee aan epische multiplayer gevechten met maximaal 64 spelers. Stem je tactiek af op de adembenemende omgevingen en totale verwoesting. Strijd als infanterie of bestuur ongelooflijke voertuigen te land, ter zee en in de lucht (varierend van tanks en motoren op de grond tot dubbeldekkers en gigantische oorlogsschepen), en stem je speelstijl af op de meest dynamische gevechten in de geschiedenis van Battlefield.; http://media-www-battlefieldwebcore.spark.ea.com/content/battlefield-portal/nl_NL/_global_/_jcr_content/ccm/componentwrapper_0/components/opengraph/ogImage.img.jpg"
//        , gameParser.removeFromLine(line, "0"));
//    }
//
//    @Test
//    public void everyLineBecomesGame() throws Exception {
//        gameParser.fillLines();
//        gameParser.addGames();
//        assertEquals(gameParser.getLines().size(), gameParser.getGames().size());
//    }
//
//    @Test
//    public void gamesToDB() throws Exception {
//        TestDB.getInstance().getGameCollection().drop();
//        assertEquals(0, TestDB.getInstance().getGameCollection().count());
//
//        //Same as GameParser gamesToDB method:
//        TestDB.getInstance().getGameCollection().drop();
//        GameCollectionManager manager = new GameCollectionManager();
//        gameParser.addGames();
//        gameParser.getGames().forEach(game -> TestDB.getInstance().getGameCollection().insertOne(manager.createGameDocument(game.getId(), game.getName(), game.getDescription(), game.getPublisher(), game.getPrice(), game.getAge(), game.getPlatform(), game.getGenre(), game.getAmount(), game.getReleaseDate(), game.getImage())));
//
//        assertTrue(TestDB.getInstance().getGameCollection().count() > 0);
//        assertEquals(gameParser.getGames().size(), TestDB.getInstance().getGameCollection().count());
//        TestDB.getInstance().getGameCollection().drop();
//        assertEquals(0, TestDB.getInstance().getGameCollection().count());
//    }

}