package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoDatabase {
	
	private MongoClient mongo;
	private DB db;
	private DBCollection gameCollection;
	private DBCollection userCollection;
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private static MongoDatabase instance;
	
	@SuppressWarnings("deprecation")
	private MongoDatabase() {
		this.mongo = new MongoClient("localhost", 27017);
		this.db = mongo.getDB("project5");
		this.gameCollection = db.getCollection("games");
		this.userCollection = db.getCollection("users");
	}
	
	public void insertUser(String username, String password, int age, String email, boolean admin, BasicDBObject address, List<Integer> gameIdList, List<DBObject> historyItems, List<Integer> favouriteList, List<Integer> wishList) {
		if (databaseHasUser(username))
			return;
		BasicDBObject userDocument = new BasicDBObject();
		userDocument.put("username", username);
		userDocument.put("password", password);
		userDocument.put("age", age);
		userDocument.put("email", email);
		userDocument.put("admin", admin);
		userDocument.put("address", address);
		userDocument.put("cart_items", gameIdList);
		userDocument.put("purchase_history", historyItems);
		userDocument.put("favourite_list", favouriteList);
		userDocument.put("wish_list", wishList);
		userCollection.insert(userDocument);
	}
	
	@SuppressWarnings("unchecked")
	public void addHistoryDocument(String username, DBObject historyDocument) {
		if (!databaseHasUser(username))
			return;
		BasicDBObject userDocument = (BasicDBObject) getUserDocument(username);
		List<DBObject> historyItems = userDocument.get("purchase_history") == null ? new ArrayList<>() : (List<DBObject>) userDocument.get("purchase_history");
		historyItems.add(historyDocument);
		userDocument.replace("purchase_history", historyItems);
		userCollection.update(getUserDocument(username), userDocument);
	}
	
	@SuppressWarnings("unchecked")
	public void addToFavouriteList(String username, int gameId) {
		if (!databaseHasUser(username))
			return;
		if (!userHasGame(username, gameId))
			return;
		BasicDBObject userDocument = (BasicDBObject) getUserDocument(username);
		List<Integer> favouriteList = userDocument.get("favourite_list") == null ? new ArrayList<>() : (List<Integer>) userDocument.get("favourite_list");
		favouriteList.add(gameId);
		userDocument.replace("favourite_list", favouriteList);
		userCollection.update(getUserDocument(username), userDocument);
	}
	
	@SuppressWarnings("unchecked")
	public boolean userHasGame(String username, int gameId) {
		if (!databaseHasUser(username))
			return false;
		BasicDBObject userDocument = (BasicDBObject) getUserDocument(username);
		List<DBObject> historyItems = userDocument.get("purchase_history") == null ? new ArrayList<>() : (List<DBObject>) userDocument.get("purchase_history");
		for (DBObject item : historyItems)
			if ((int) item.get("id") == gameId)
				return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void addToWishList(String username, int gameId) {
		if (!databaseHasUser(username))
			return;
		BasicDBObject userDocument = (BasicDBObject) getUserDocument(username);
		List<Integer> wishList = userDocument.get("wish_list") == null ? new ArrayList<>() : (List<Integer>) userDocument.get("wish_list");
		wishList.add(gameId);
		userDocument.replace("wish_list", wishList);
		userCollection.update(getUserDocument(username), userDocument);
	}
	
	public DBObject createHistoryDocument(int gameId, Date purchaseDate) {
		BasicDBObject historyDocument = new BasicDBObject();
		historyDocument.put("id", gameId);
		historyDocument.put("purchase_date", dateFormat.format(purchaseDate));
		return historyDocument;
	}
	
	public BasicDBObject createAddressDocument(String country, String city, String street, int number, String postalcode) {
		BasicDBObject addressDocument = new BasicDBObject();
		addressDocument.put("country", country);
		addressDocument.put("city", city);
		addressDocument.put("street", street);
		addressDocument.put("number", number);
		addressDocument.put("postalcode", postalcode);
		return addressDocument;
	}
	
	public BasicDBObject createGameDocument(int id, String name, String description, float price, int age, String platform, String genre, Date releaseDate) {
		BasicDBObject gameDocument = new BasicDBObject();
		gameDocument.put("id", id);
		gameDocument.put("name", name);
		gameDocument.put("description", description);
		gameDocument.put("price", price);
		gameDocument.put("age", age);
		gameDocument.put("platform", platform);
		gameDocument.put("genre", genre);
		gameDocument.put("release_date", dateFormat.format(releaseDate));
		return gameDocument;
	}
	
	public DBObject getGameDocument(int id) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		return gameCollection.find(query).next();
	}
	
	public DBObject getUserDocument(String username) {
		BasicDBObject query = new BasicDBObject();
		query.put("username", username);
		return userCollection.find(query).next();
	}
	
	public void insertNewGameDocument(BasicDBObject gameDocument) {
		if (!databaseHasGame(gameDocument.getInt("id")))
			gameCollection.insert(gameDocument);
	}
	
	private boolean databaseHasGame(int id) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		return gameCollection.find(query).hasNext();
	}
	
	private boolean databaseHasUser(String username) {
		BasicDBObject query = new BasicDBObject();
		query.put("username", username);
		return userCollection.find(query).hasNext();
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
	
	public void printValues(DBCollection collection) {
		DBCursor cursor = collection.find();
		while (cursor.hasNext())
			System.out.println(cursor.next());
	}
	
	public static MongoDatabase getInstance() {
		if (instance == null)
			instance = new MongoDatabase();
		return instance;
	}

}
