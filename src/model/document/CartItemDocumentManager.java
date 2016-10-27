package model.document;

import org.bson.Document;

import model.Database;

public class CartItemDocumentManager extends DocumentManager {

	public CartItemDocumentManager(Document doc) {
		super(doc, Database.getInstance().getUserCollection());
		setFilter(new Document("id", getId()));
	}

	public int getId() {
		return document.getInteger("id");
	}

	public int getAmount() {
		return document.getInteger("amount");
	}

	public void setAmount(int amount) {
		update(new Document("amount", amount));
	}

}
