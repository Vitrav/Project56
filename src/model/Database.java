package model;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.bson.Document;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

//This class makes a connection with the (mongo) database using the singleton pattern
public class Database {

	private MongoClient mongo;
	private MongoDatabase db;
	private MongoCollection<Document> gameCollection;
	private MongoCollection<Document> userCollection;

	private final String host = "ds161518.mlab.com";
    private final int port = 61518;
    private final String databaseName = "project6";
    private final String username = "admin";
    private final String password = "supergames";

	private static Database instance;

	private Database() {
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

	public static Database getInstance() {
		if (instance == null)
			instance = new Database();
		return instance;
	}

	//cmd connection:
    //mongo ds131139.mlab.com:31139/project5 -u admin -p supergames

}
