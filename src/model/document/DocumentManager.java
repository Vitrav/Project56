package model.document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bson.Document;

public class DocumentManager {

	protected final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	protected Document document;

	public DocumentManager(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

}
