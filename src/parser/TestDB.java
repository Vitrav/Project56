package parser;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Database;
import org.bson.Document;

import java.util.Arrays;

//This class makes a connection with the (mongo) database using the singleton pattern only for testing
public class TestDB {

    private MongoClient mongo;
    private MongoDatabase db;
    private MongoCollection<Document> gameCollection;
    private MongoCollection<Document> userCollection;

    private final String host = "ds133249.mlab.com";
    private final int port = 33249;
    private final String databaseName = "unittest";
    private final String username = "unit";
    private final String password = "test";

    private static TestDB instance;

    private TestDB() {
        this.mongo = new MongoClient(new ServerAddress(host, port), Arrays.asList(MongoCredential.createCredential(username, databaseName, password.toCharArray())));
        this.db = mongo.getDatabase(databaseName);
        this.gameCollection = db.getCollection("games");
        this.userCollection = db.getCollection("users");
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public MongoDatabase getDatabase() {
        return db;
    }

    public MongoCollection<Document> getGameCollection() {
        return gameCollection;
    }

    public MongoCollection<Document> getUserCollection()  {
        return userCollection;
    }


    public static TestDB getInstance() {
        if (instance == null)
            instance = new TestDB();
        return instance;
    }

    //mongo ds133249.mlab.com:33249/unittest -u unit -p test
}


