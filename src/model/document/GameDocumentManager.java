package model.document;

import java.util.Date;

import org.bson.Document;

public class GameDocumentManager extends DocumentManager {
	
	public GameDocumentManager(Document document) {
		super(document);
	}
	
	public String getName() {
		return document.getString("name");
	}
	
	public void setName(String name) {
		document.replace("name", name);
	}
	
	public String getDescription() {
		return document.getString("description");
	}
	
	public void setDescription(String description) {
		document.replace("description", description);
	}
	
	public String getPublisher() {
		return document.getString("publisher");
	}
	
	public double getPrice() {
		return document.getDouble("price");
	}
	
	public void setPrice(double price) {
		document.replace("price", price);
	}
	
	public int getAge() {
		return document.getInteger("age");
	}
	
	public void setAge(int age) {
		document.replace("age", age);
	}
	
	public String getPlatform() {
		return document.getString("platform");
	}
	
	public void setPlatform(String platform) {
		document.replace("platform", platform);
	}
	
	public String getGenre() {
		return document.getString("genre");
	}
	
	public void setGenre(String genre) {
		document.replace("genre", genre);
	}
	
	public int getAmountInStock() {
		return document.getInteger("amount_in_stock");
	}
	
	public void setAmountInStock(int amount) {
		document.replace("amount_in_stock", amount);
	}
	
	public String getDate() {
		return document.getString("release_date");
	}
	
	public void setDate(Date date) {
		 document.replace("release_date", dateFormat.format(date));
	}

}
