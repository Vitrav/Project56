package model.collection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import model.Database;

public class CollectionManager {

	protected final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	protected MongoCollection<Document> collection;

	public CollectionManager(MongoCollection<Document> collection) {
		this.collection = collection;
	}

	public void printValues() {
		FindIterable<Document> cursor = collection.find();
		while (cursor.iterator().hasNext())
			System.out.println(cursor.iterator().next());
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public void dropCollection() {
		Database.getInstance().dropCollection(collection);
	}

}
