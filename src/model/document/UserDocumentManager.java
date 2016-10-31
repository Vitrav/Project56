package model.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import model.Database;
import model.collection.UserCollectionManager;
import main.Application.*;
public class UserDocumentManager extends DocumentManager {

	public UserDocumentManager(Document doc) {
		super(doc, Database.getInstance().getUserCollection());
		setFilter(new Document("username", getName()));
	}

	public String getName() {
		return document.getString("username");
	}

	public void setName(String name) {
		update(new Document("username", name));
		setFilter(new Document("username", name));
	}

	public String getPassword() {
		return document.getString("password");
	}

	public void setPassword(String password) {
		update(new Document("password", password));
	}

	public int getAge() {
		return document.getInteger("age");
	}

	public void setAge(int age) {
		update(new Document("age", age));
	}

	public String getDateOfBirth() {
		return document.getString("date_of_birth");
	}

	public void setDateOfBirth(Date dateOfBirth) {
		String date = dateFormat.format(dateOfBirth);
		update(new Document("date_of_birth", date.substring(date.indexOf(" ") + 1, date.length())));
	}

	public String getEmail() {
		return document.getString("email");
	}

	public void setEmail(String email) {
		update(new Document("email", email));
	}

	public boolean isAdmin() {
		return document.getBoolean("admin");
	}

	public void setAdmin() {
		update(new Document("admin", true));
	}

	public boolean wishListIsPrivate() {
		return document.getBoolean("private_wish_list");
	}

	public void setWishList(boolean toPrivate) {
		update(new Document("private_wish_list", toPrivate));
	}

	public Document getAddress() {
		return (Document) document.get("address");
	}
	
	public void setAddress(Document address) {
		update(new Document("address", address));
	}

	@SuppressWarnings("unchecked")
	public List<Document> getCartItems() {
		return (List<Document>) document.get("cart_items");
	}

	public void setCartItems(List<Document> cartItems) {
		update(new Document("cart_items", cartItems));
	}

	public void addCartItem(int gameId) {
		List<Document> items = getCartItems();
		items.add(new UserCollectionManager().createCartItemDocument(gameId));
		setCartItems(items);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getPurchaseHistory() {
		return (List<Integer>) document.get("purchase_history");
	}

	public void setPurchaseHistory(List<Integer> historyItems) {
		update(new Document("purchase_history", historyItems));
	}

	public void addHistoryItem(int gameId) {
		List<Integer> items = getPurchaseHistory() == null ? new ArrayList<>() : getPurchaseHistory();
		items.add(gameId);
		setPurchaseHistory(items);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getFavouriteList() {
		return (List<Integer>) document.get("favourite_list");
	}

	public void setFavouriteList(List<Integer> favouriteList) {
		update(new Document("favourite_list", favouriteList));
	}

	public void addFavouriteItem(int gameId) {
		List<Integer> items = getFavouriteList() == null ? new ArrayList<>() : getFavouriteList();
		items.add(gameId);
		setFavouriteList(items);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getWishList() {
		return (List<Integer>) document.get("wish_list");
	}

	public void setWishList(List<Integer> wishList) {
		update(new Document("wish_list", wishList));
	}

	public void addWishItem(int gameId) {
		List<Integer> items = getWishList() == null ? new ArrayList<>() : getWishList();
		items.add(gameId);
		setWishList(items);
	}

}
