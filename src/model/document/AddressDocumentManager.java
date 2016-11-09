package model.document;

import org.bson.Document;

import model.Database;

//Manager for address documents which are inside an user documents.
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
