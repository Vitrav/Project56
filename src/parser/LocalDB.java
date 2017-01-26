package parser;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

//This class makes a connection with the (mongo) database using the singleton pattern only for testing
public class LocalDB {

    private final MongoClient mongo;
    private final MongoDatabase db;

    private MongoCollection<Document> gameCollection;

    private MongoCollection<Document> userCollection;

    private static LocalDB instance;

    private LocalDB() {
        this.mongo = new MongoClient("localhost", 27017);
        this.db = mongo.getDatabase("project5");
        this.gameCollection = db.getCollection("games");
        this.userCollection = db.getCollection("users");
    }

    public MongoCollection<Document> getGameCollection() {
        return gameCollection;
    }

    public MongoCollection<Document> getUserCollection() {
        return userCollection;
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public MongoDatabase getDatabase() {
        return db;
    }

    public static LocalDB getInstance() {
        if (instance == null)
            instance = new LocalDB();
        return instance;
    }
}


