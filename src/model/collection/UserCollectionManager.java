package model.collection;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import model.Database;
import user.Address;
import user.User;

import javax.print.Doc;

//This class can be used to get a userDocument by username or to insert a new user in the database.
public class UserCollectionManager extends CollectionManager {

	public UserCollectionManager() {
		super(Database.getInstance().getUserCollection());
	}

	public Document getUserDocument(String username) {
		Document query = new Document();
		query.put("username", username);
		return collection.find(query).iterator().hasNext() ? collection.find(query).iterator().next() : null;
	}

	public void insertUser(User user) {
		insertUser(user, Arrays.asList(), Arrays.asList(),  Arrays.asList(), Arrays.asList());
	}

	public void insertUser(User user, List<Document> cartItems, List<Document> historyItems, List<Integer> favouriteList, List<Integer> wishList) {
		Document userDocument = new Document();
		userDocument.put("username", user.getUsername());
		userDocument.put("password", user.getHashedPassword());
        userDocument.put("salt", user.getSalt());
		userDocument.put("age", user.getAge());
		userDocument.put("date_of_birth", dateFormat.format(user.getDateOfBirth()));
		userDocument.put("email", user.getEmail());
		userDocument.put("admin", user.getIsAdmin());
		userDocument.put("private_wish_list", user.getHasPrivateWishList());
		userDocument.put("address", createAddressDocument(user.getAddress()));
		userDocument.put("cart_items", cartItems);
		userDocument.put("purchase_history", historyItems);
		userDocument.put("favourite_list", favouriteList);
		userDocument.put("wish_list", wishList);
		userDocument.put("is_blocked", user.getIsBlocked());
		collection.insertOne(userDocument);
	}

	public Document createAddressDocument(Address address) {
		Document addressDocument = new Document();
		addressDocument.put("country", address.getCountry());
		addressDocument.put("city", address.getCity());
		addressDocument.put("street", address.getStreet());
		addressDocument.put("number", address.getNumber());
		addressDocument.put("postalcode", address.getPostalCode());
		return addressDocument;
	}

	public Document createCartItemDocument(int gameId) {
		Document cartItemDocument = new Document();
		cartItemDocument.put("id", gameId);
		cartItemDocument.put("amount", 1);
		return cartItemDocument;
	}

	public Document createHistoryDocument(int gameId, int amount) {
		return createHistoryDocument(gameId, amount, new Date());
	}

	public Document createHistoryDocument(int gameId, int amount, Date purchaseDate) {
		Document historyDocument = new Document();
		historyDocument.put("id", gameId);
		historyDocument.put("amount", amount);
		historyDocument.put("purchase_date", dateFormat.format(purchaseDate));
		return historyDocument;
	}

	public void deleteUser(String userName) {
		Document query = new Document();
        query.put("username", userName);
        collection.findOneAndDelete(query);
	}

}
