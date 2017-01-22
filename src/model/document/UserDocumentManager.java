package model.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.RegistrationController;
import model.collection.GameCollectionManager;
import org.bson.Document;

import model.Database;
import model.collection.UserCollectionManager;
import main.Application.*;
import user.UserController;

import javax.print.Doc;
import javax.xml.crypto.Data;

import static viewutil.RequestUtil.getSessionCurrentUser;

//Manager for user documents, updates are filtered by username.
public class UserDocumentManager extends DocumentManager {

    private AddressDocumentManager addressDocManager;

	public UserDocumentManager(String username) {
		super(new UserCollectionManager().getUserDocument(username), Database.getInstance().getUserCollection());
		setFilter(new Document("username", getName()));
	}

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

	public String getSalt() {
		return document.getString("salt");
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

	public Date getDateOfBirthAsDate() {
        try {
            return dateFormat.parse(document.getString("date_of_birth"));
        } catch (Exception e) {
            return null;
        }
    }

    private Date getDateOfBirthAsDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

	public void setDateOfBirth(Date dateOfBirth) {
        int age = RegistrationController.calculateAge(dateOfBirth);
        setAge(age);
        update(new Document("date_of_birth", dateFormat.format(dateOfBirth)));
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

	public void setAdmin(boolean admin) {
		update(new Document("admin", admin));
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

	public double getTotalGamePrice(int gameId) {
		if (!new UserController(getName()).userHasGame(gameId))
			return 0.0;
		for (Document item : getCartItems())
			if (item.getInteger("id") == gameId)
				return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId)).getPrice() * item.getInteger("amount");
		return 0.0;
	}

	public int getGameAmount(int gameId) {
		if (!new UserController(getName()).userHasGame(gameId))
			return 0;
		for (Document item : getCartItems())
			if (item.getInteger("id") == gameId)
				return item.getInteger("amount");
		return 0;
	}

	public void incGameAmount(int gameId) {
		if (!new UserController(getName()).userHasGame(gameId))
			return;
		List<Document> items = getCartItems();
		items.forEach(item -> {
			if (item.getInteger("id") == gameId)
				item.put("amount", item.getInteger("amount") + 1);
		});
		setCartItems(items);
	}

	public int countTotalProducts() {
        int total = 0;
        for (Document item : getCartItems())
            total += item.getInteger("amount");
        return total;
    }

    public double countTotalPrice() {
        double total = 0.0;
        for (Document item : getCartItems())
            total += getTotalGamePrice(item.getInteger("id"));
        return total;
    }

	public void setCartItems(List<Document> cartItems) {
		update(new Document("cart_items", cartItems));
	}

	public void addCartItem(int gameId) {
		List<Document> items = getCartItems();
		for (Document item : items){
			if (item.getInteger("id") == gameId){
				incGameAmount(gameId);
				return;
			}
		}
		items.add(new UserCollectionManager().createCartItemDocument(gameId));
		setCartItems(items);
	}

	public void removeCartItem(int gameId) {
        List<Document> items = getCartItems() == null ? new ArrayList<>() : getCartItems();
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getInteger("id") == gameId) {
				items.remove(i);
				setCartItems(items);
			}
		}
    }

	@SuppressWarnings("unchecked")
	public List<Document> getPurchaseHistory() {
		return (List<Document>) document.get("purchase_history");
	}

	public void setPurchaseHistory(List<Document> historyItems) {
		update(new Document("purchase_history", historyItems));
	}

	public void addHistoryItem(int gameId, int amount) {
		List<Document> items = getPurchaseHistory() == null ? new ArrayList<>() : getPurchaseHistory();
		items.add(new UserCollectionManager().createHistoryDocument(gameId, amount));
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

	public void removeFavItem(int gameId) {
        List<Integer> items = getFavouriteList() == null ? new ArrayList<>() : getFavouriteList();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == gameId) {
                items.remove(i);
                break;
            }
        }
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
		if (!items.contains(gameId)) {
			items.add(gameId);
			setWishList(items);
		}
	}

	public void removeWishItem(int gameId) {
        List<Integer> items = getWishList() == null ? new ArrayList<>() : getWishList();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == gameId) {
                items.remove(i);
                break;
            }
        }
        setWishList(items);
    }

    public boolean hasWishListItem(int gameId) {
        List<Integer> items = getWishList() == null ? new ArrayList<>() : getWishList();
        for (int i = 0; i < items.size(); i++)
            if (items.get(i) == gameId)
                return true;
        return false;
    }

	public boolean getIsBlocked() {
		return (boolean) document.get("is_blocked");
	}

	public void setBlocked(boolean block) {
		update(new Document("is_blocked", block));
	}

	public AddressDocumentManager getAddressDocManager() {
        if (addressDocManager == null)
            addressDocManager = new AddressDocumentManager((Document) document.get("address"));
		return addressDocManager;
	}

}
