package model.document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;

//Super class for all the document managers. The document managers are used to get or updates values from a specific document.
public class DocumentManager {

	protected final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	protected Document document;
	protected MongoCollection<Document> collection;
	private Bson filter;
	
	public DocumentManager(Document document, MongoCollection<Document> collection) {
		this.document = document;
		this.collection = collection;
	}

	public Document getDocument() {
		return document;
	}
	
	protected void setFilter(Bson filter) {
		this.filter = filter;
	}
	
	private Bson getUpdateOperationDocument(Bson newValue) {
		return new Document("$set", newValue);
	}
	
	protected void update(Bson newValue) {
		collection.updateOne(filter, getUpdateOperationDocument(newValue));
	}

}
