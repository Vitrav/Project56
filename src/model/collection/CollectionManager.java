package model.collection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import model.MongoDatabase;

public class CollectionManager {
	
	protected final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	protected DBCollection collection;
	
	public CollectionManager(DBCollection collection) {
		this.collection = collection;
	}
	
	public void printValues() {
		DBCursor cursor = collection.find();
		while (cursor.hasNext())
			System.out.println(cursor.next());
	}
	
	public DBCollection getCollection() {
		return collection;
	}
	
	public void dropCollection() {
		MongoDatabase.getInstance().dropCollection(collection);
	}

}
