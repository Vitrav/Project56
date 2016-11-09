package model.document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import model.Database;

//Manager for game documents, updates are filtered by game_id.
public class GameDocumentManager extends DocumentManager {

	public GameDocumentManager(Document doc) {
		super(doc, Database.getInstance().getGameCollection());
		setFilter(new Document("id", getId()));
	}

	public int getId() {
		return document.getInteger("id");
	}

	public String getName() {
		return document.getString("name");
	}

	public void setName(String name) {
		update(new Document("name", name));
	}

	public String getDescription() {
		return document.getString("description");
	}

	public void setDescription(String description) {
		update(new Document("description", description));
	}

	public String getPublisher() {
		return document.getString("publisher");
	}

	public double getPrice() {
		return document.getDouble("price");
	}

	public void setPrice(double price) {
		update(new Document("price", price));
	}

	public int getAge() {
		return document.getInteger("age");
	}

	public void setAge(int age) {
		update(new Document("age", age));
	}

	public String getPlatform() {
		return document.getString("platform");
	}

	public void setPlatform(String platform) {
		update(new Document("platform", platform));
	}

	public String getGenre() {
		return document.getString("genre");
	}

	public void setGenre(String genre) {
		update(new Document("genre", genre));
	}

	public int getAmountInStock() {
		return document.getInteger("amount_in_stock");
	}

	public void setAmountInStock(int amount) {
		update(new Document("amount_in_stock", amount));
	}

	public String getDate() {
		return document.getString("release_date");
	}

	public void setDate(Date date) {
		update(new Document("release_date", dateFormat.format(date)));
	}

}
