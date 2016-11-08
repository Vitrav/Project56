package model.document;

import org.bson.Document;

import model.Database;

public class AddressDocumentManager extends DocumentManager {
	
	public AddressDocumentManager(Document doc) {
        super(doc, Database.getInstance().getUserCollection());
	}

	public String getCountry() {
		return document.getString("country");
	}

	public String getCity() {
		return document.getString("city");
	}

	public String getStreet() {
		return document.getString("street");
	}

	public String getNumber() {
		return document.getString("number");
	}

	public String getPostalCode() {
		return document.getString("postalcode");
	}

}
