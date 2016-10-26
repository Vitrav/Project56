package model.collection;

import java.util.Date;

import com.mongodb.BasicDBObject;

import model.MongoDatabase;

public class GameCollectionManager extends CollectionManager {
	
	public GameCollectionManager() {
		super(MongoDatabase.getInstance().getGameCollection());
	}

	public boolean databaseHasGame(int id) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		return collection.find(query).hasNext();
	}
	
	public BasicDBObject getGameDocument(int id) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		return (BasicDBObject) collection.find(query).next();
	}
	
	public boolean insertNewGameDocument(BasicDBObject gameDocument) {
		if (!databaseHasGame(gameDocument.getInt("id"))) {
			collection.insert(gameDocument);
			return true;
		}
		return false;
	}
	
	public BasicDBObject createGameDocument(int id, String name, String description, String publisher, double price, int age, String platform, String genre, int amount, Date releaseDate) {
		if (databaseHasGame(id))
			return getGameDocument(id);
		BasicDBObject gameDocument = new BasicDBObject();
		gameDocument.put("id", id);
		gameDocument.put("name", name);
		gameDocument.put("description", description);
		gameDocument.put("publisher", publisher);
		gameDocument.put("price", price);
		gameDocument.put("age", age);
		gameDocument.put("platform", platform);
		gameDocument.put("genre", genre);
		gameDocument.put("amount_in_stock", amount);
		gameDocument.put("release_date", dateFormat.format(releaseDate));
		return gameDocument;
	}

}
