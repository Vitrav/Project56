package model.collection;

import java.util.Date;
import java.util.List;

import org.bson.Document;

import model.Database;

public class UserCollectionManager extends CollectionManager {

	public UserCollectionManager() {
		super(Database.getInstance().getUserCollection());
	}

	public boolean databaseHasUser(String username) {
		Document query = new Document();
		query.put("username", username);
		return collection.find(query).iterator().hasNext();
	}

	public Document getUserDocument(String username) {
		Document query = new Document();
		query.put("username", username);
		return collection.find(query).iterator().next();
	}
//	public boolean insertUser(String username, String password, int age, Date dateOfBirth, String email, boolean admin, Document address, List<Document> cartItems, List<Document> historyItems, List<Integer> favouriteList, List<Integer> wishList) {
//		if (databaseHasUser(username))
//			return false;
//		return insertUser(username, password, age, dateOfBirth, email, admin, address, cartItems, historyItems, favouriteList, wishList);
//	}

	public boolean insertUser(String username, String password, int age, Date dateOfBirth, String email, boolean admin, boolean privateWishList, Document address, List<Document> cartItems, List<Document> historyItems, List<Integer> favouriteList, List<Integer> wishList) {
		if (databaseHasUser(username))
			return false;
		Document userDocument = new Document();
		String date = dateFormat.format(dateOfBirth);
		userDocument.put("username", username);
		userDocument.put("password", password);
		userDocument.put("age", age);
		userDocument.put("date_of_birth", date.substring(date.indexOf(" ") + 1, date.length()));
		userDocument.put("email", email);
		userDocument.put("admin", admin);
		userDocument.put("private_wish_list", privateWishList);
		userDocument.put("address", address);
		userDocument.put("cart_items", cartItems);
		userDocument.put("purchase_history", historyItems);
		userDocument.put("favourite_list", favouriteList);
		userDocument.put("wish_list", wishList);
		collection.insertOne(userDocument);
		return true;
	}

	public Document createAddressDocument(String country, String city, String street, String number, String postalcode) {
		Document addressDocument = new Document();
		addressDocument.put("country", country);
		addressDocument.put("city", city);
		addressDocument.put("street", street);
		addressDocument.put("number", number);
		addressDocument.put("postalcode", postalcode);
		return addressDocument;
	}

	public Document createCartItemDocument(int gameId) {
		Document cartItemDocument = new Document();
		cartItemDocument.put("id", gameId);
		cartItemDocument.put("amount", 1);
		return cartItemDocument;
	}

	public Document createHistoryDocument(int gameId, Date purchaseDate) {
		Document historyDocument = new Document();
		historyDocument.put("id", gameId);
		historyDocument.put("purchase_date", dateFormat.format(purchaseDate));
		return historyDocument;
	}

}
