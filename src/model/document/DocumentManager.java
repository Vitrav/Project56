package model.document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.mongodb.BasicDBObject;

public class DocumentManager {
	
	protected final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	protected BasicDBObject document;
	
	public DocumentManager(BasicDBObject document) {
		this.document = document;
	}
	
	public BasicDBObject getDocument() {
		return document;
	}

}
