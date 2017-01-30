package model.document;

import model.collection.GameCollectionManager;
import org.bson.Document;
//Manager for the history item documents, can retrieve all information per item
public class HistoryDocumentManager {

    private final Document doc;

    public HistoryDocumentManager(Document doc) {
        this.doc = doc;
    }

    public int getId() {
        return doc.getInteger("id");
    }

    public String getDate() {
        return doc.getString("purchase_date");
    }

    public int getAmount() {
        return doc.getInteger("amount");
    }

    public double getPrice() {
        return getGameManager().getPrice() * getAmount();
    }

    public GameDocumentManager getGameManager() {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(getId()));
    }
}
