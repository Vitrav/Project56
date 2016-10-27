package model.document;

import org.bson.Document;

public class CartItemDocumentManager extends DocumentManager {

	public CartItemDocumentManager(Document document) {
		super(document);
	}

	public int getId() {
		return document.getInteger("id");
	}

	public int getAmount() {
		return document.getInteger("amount");
	}

	public void setAmount(int amount) {
		document.replace("amount", amount);
	}

}
