package model.document;

import com.mongodb.BasicDBObject;

public class CartItemDocumentManager extends DocumentManager {

	public CartItemDocumentManager(BasicDBObject document) {
		super(document);
	}
	
	public int getId() {
		return document.getInt("id");
	}
	
	public int getAmount() {
		return document.getInt("amount");
	}
	
	public void setAmount(int amount) {
		document.replace("amount", amount);
	}

}
