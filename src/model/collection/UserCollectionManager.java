package model.collection;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import model.Database;
import user.Address;
import user.User;

public class UserCollectionManager extends CollectionManager {

	public UserCollectionManager() {
		super(Database.getInstance().getUserCollection());
	}

	public Document getUserDocument(String username) {
		Document query = new Document();
		query.put("username", username);
		return collection.find(query).iterator().next();
	}

	public void insertUser(User user) {
		insertUser(user, Arrays.asList(), Arrays.asList(),  Arrays.asList(), Arrays.asList());
	}

	public void insertUser(User user, List<Document> cartItems, List<Document> historyItems, List<Integer> favouriteList, List<Integer> wishList) {
		Document userDocument = new Document();
		String date = dateFormat.format(user.getDateOfBirth());
		userDocument.put("username", user.getUsername());
		userDocument.put("password", user.getHashedPassword());
        userDocument.put("salt", user.getSalt());
		userDocument.put("age", user.getAge());
		userDocument.put("date_of_birth", date.substring(date.indexOf(" ") + 1, date.length()));
		userDocument.put("email", user.getEmail());
		userDocument.put("admin", user.getIsAdmin());
		userDocument.put("private_wish_list", user.getHasPrivateWishList());
		userDocument.put("address", createAddressDocument(user.getAddress()));
		userDocument.put("cart_items", cartItems);
		userDocument.put("purchase_history", historyItems);
		userDocument.put("favourite_list", favouriteList);
		userDocument.put("wish_list", wishList);
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

	public Document createHistoryDocument(int gameId, Date purchaseDate) {
		Document historyDocument = new Document();
		historyDocument.put("id", gameId);
		historyDocument.put("purchase_date", dateFormat.format(purchaseDate));
		return historyDocument;
	}

}
