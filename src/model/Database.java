package model;

import org.bson.Document;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Database {

	private MongoClient mongo;
	private MongoDatabase db;
	private MongoCollection<Document> gameCollection;
	private MongoCollection<Document> userCollection;

	private static Database instance;

	private Database() {
		this.mongo = new MongoClient("localhost", 27017);
		this.db = mongo.getDatabase("project5");
		this.gameCollection = db.getCollection("games");
		this.userCollection = db.getCollection("users");
	}

	public Mongo getMongo() {
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

	public void dropCollection(MongoCollection<Document> collection) {
		collection.drop();
	}

	public static Database getInstance() {
		if (instance == null)
			instance = new Database();
		return instance;
	}

}
