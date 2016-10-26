package model;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoDatabase {
	
	private MongoClient mongo;
	private DB db;
	private DBCollection gameCollection;
	private DBCollection userCollection;
	
	private static MongoDatabase instance;
	
	@SuppressWarnings("deprecation")
	private MongoDatabase() {
		this.mongo = new MongoClient("localhost", 27017);
		this.db = mongo.getDB("project5");
		this.gameCollection = db.getCollection("games");
		this.userCollection = db.getCollection("users");
	}
	
	public Mongo getMongo() {
		return mongo;
	}
	
	public DB getDatabase() {
		return db;
	}
	
	public DBCollection getGameCollection() {
		return gameCollection;
	}
	
	public DBCollection getUserCollection()  {
		return userCollection;
	}
	
	public void dropCollection(DBCollection collection) {
		collection.drop();
	}
	
	public static MongoDatabase getInstance() {
		if (instance == null)
			instance = new MongoDatabase();
		return instance;
	}

}
