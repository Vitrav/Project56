package model.collection;

import java.util.Date;

import org.bson.Document;

import model.Database;

public class GameCollectionManager extends CollectionManager {
	
	public GameCollectionManager() {
		super(Database.getInstance().getGameCollection());
	}

	public boolean databaseHasGame(int id) {
		Document query = new Document();
		query.put("id", id);
		return collection.find(query).iterator().hasNext();
	}
	
	public Document getGameDocument(int id) {
		Document query = new Document();
		query.put("id", id);
		return collection.find(query).iterator().next();
	}
	
	public boolean insertNewGameDocument(Document gameDocument) {
		if (!databaseHasGame(gameDocument.getInteger("id"))) {
			collection.insertOne(gameDocument);
			return true;
		}
		return false;
	}
	
	public Document createGameDocument(int id, String name, String description, String publisher, double price, int age, String platform, String genre, int amount, Date releaseDate) {
		if (databaseHasGame(id))
			return getGameDocument(id);
		Document gameDocument = new Document();
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
