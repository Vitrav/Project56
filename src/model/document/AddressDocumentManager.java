package model.document;

import com.mongodb.BasicDBObject;

public class AddressDocumentManager extends DocumentManager {

	public AddressDocumentManager(BasicDBObject document) {
		super(document);
	}
	
	public String getCountry() {
		return document.getString("country");
	}
	
	public void setCountry(String country) {
		document.replace("country", country);
	}
	
	public String getCity() {
		return document.getString("city");
	}
	
	public void setCity(String city) {
		document.replace("city", city);
	}
	
	public String getStreet() {
		return document.getString("street");
	}
	
	public void setStreet(String street) {
		document.replace("street", street);
	}
	
	public String getNumber() {
		return document.getString("number");
	}
	
	public void setNumber(String number) {
		document.replace("number", number);
	}
	
	public String getPostalCode() {
		return document.getString("postalcode");
	}
	
	public void setPostalCode(String postalcode) {
		document.replace("postalcode", postalcode);
	}

}
