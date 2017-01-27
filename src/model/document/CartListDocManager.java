package model.document;

import model.collection.GameCollectionManager;
import org.bson.Document;
import user.UserController;

import java.util.ArrayList;
import java.util.List;

public class CartListDocManager {

    private final List<Document> documents = new ArrayList<>();

    public CartListDocManager(List<Document> docs) {
        docs.forEach(x -> documents.add(x));
    }

    public int countTotalProducts() {
        int total = 0;
        for (Document item : documents)
            total += item.getInteger("amount");
        return total;
    }

    public double getTotalGamePrice(int gameId) {
        for (Document item : documents)
            if (item.getInteger("id") == gameId)
                return roundOff(new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId)).getPrice() * item.getInteger("amount"));
        return 0.0;
    }

    public double countTotalPrice() {
        double total = 0.0;
        for (Document item : documents)
            total += getTotalGamePrice(item.getInteger("id"));
        return roundOff(total);
    }

    public int getGameAmount(int gameId) {
        for (Document item : documents)
            if (item.getInteger("id") == gameId)
                return item.getInteger("amount");
        return 0;
    }

    private double roundOff(double num) {
        return Math.round(num * 100.0) / 100.0;
    }


}
