package model.collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import model.MongoDatabase;

public class UserCollectionManager extends CollectionManager {

	public UserCollectionManager() {
		super(MongoDatabase.getInstance().getUserCollection());
	}
	
	public boolean databaseHasUser(String username) {
		BasicDBObject query = new BasicDBObject();
		query.put("username", username);
		return collection.find(query).hasNext();
	}
	
	public BasicDBObject getUserDocument(String username) {
		BasicDBObject query = new BasicDBObject();
		query.put("username", username);
		return (BasicDBObject) collection.find(query).next();
	}
	
	@SuppressWarnings("unchecked")
	public boolean addToWishList(String username, int gameId) {
		if (!databaseHasUser(username))
			return false;
		BasicDBObject userDocument = (BasicDBObject) getUserDocument(username);
		List<Integer> wishList = userDocument.get("wish_list") == null ? new ArrayList<>() : (List<Integer>) userDocument.get("wish_list");
		wishList.add(gameId);
		userDocument.replace("wish_list", wishList);
		collection.update(getUserDocument(username), userDocument);
		return true;
	}
	
	public boolean insertUser(String username, String password, int age, Date dateOfBirth, String email, boolean admin, BasicDBObject address, List<Integer> gameIdList, List<DBObject> historyItems, List<Integer> favouriteList, List<Integer> wishList) {
		if (databaseHasUser(username))
			return false;
		BasicDBObject userDocument = new BasicDBObject();
		String date = dateFormat.format(dateOfBirth);
		userDocument.put("username", username);
		userDocument.put("password", password);
		userDocument.put("age", age);
		userDocument.put("date_of_birth", date.substring(date.indexOf(" ") + 1, date.length()));
		userDocument.put("email", email);
		userDocument.put("admin", admin);
		userDocument.put("address", address);
		userDocument.put("cart_items", gameIdList);
		userDocument.put("purchase_history", historyItems);
		userDocument.put("favourite_list", favouriteList);
		userDocument.put("wish_list", wishList);
		collection.insert(userDocument);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean addHistoryDocument(String username, DBObject historyDocument) {
		if (!databaseHasUser(username))
			return false;
		BasicDBObject userDocument = (BasicDBObject) getUserDocument(username);
		List<DBObject> historyItems = userDocument.get("purchase_history") == null ? new ArrayList<>() : (List<DBObject>) userDocument.get("purchase_history");
		historyItems.add(historyDocument);
		userDocument.replace("purchase_history", historyItems);
		collection.update(getUserDocument(username), userDocument);
		return true;
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
	public boolean addToFavouriteList(String username, int gameId) {
		if (!databaseHasUser(username))
			return false;
		if (!userHasGame(username, gameId))
			return false;
		BasicDBObject userDocument = (BasicDBObject) getUserDocument(username);
		List<Integer> favouriteList = userDocument.get("favourite_list") == null ? new ArrayList<>() : (List<Integer>) userDocument.get("favourite_list");
		favouriteList.add(gameId);
		userDocument.replace("favourite_list", favouriteList);
		collection.update(getUserDocument(username), userDocument);
		return true;
	}
	
	public BasicDBObject createAddressDocument(String country, String city, String street, String number, String postalcode) {
		BasicDBObject addressDocument = new BasicDBObject();
		addressDocument.put("country", country);
		addressDocument.put("city", city);
		addressDocument.put("street", street);
		addressDocument.put("number", number);
		addressDocument.put("postalcode", postalcode);
		return addressDocument;
	}
	
	public DBObject createHistoryDocument(int gameId, Date purchaseDate) {
		BasicDBObject historyDocument = new BasicDBObject();
		historyDocument.put("id", gameId);
		historyDocument.put("purchase_date", dateFormat.format(purchaseDate));
		return historyDocument;
	}

}
