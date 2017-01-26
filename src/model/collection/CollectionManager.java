package model.collection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import model.Database;

//Super class for all collection managers.
//The managers can create and get documents but only the document managers can interact with the values.
public class CollectionManager {

	protected final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	protected MongoCollection<Document> collection;

	public CollectionManager(MongoCollection<Document> collection) {
		this.collection = collection;
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

}
