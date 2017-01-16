package controller;


import model.collection.GameCollectionManager;
import model.document.GameDocumentManager;
import org.bson.Document;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import main.viewutil.Path;

/**
 * Created by Dave on 26-10-16.
 */
public class ShopController {

    public static Route shopPage = (Request request, Response response) -> {
        GameCollectionManager gameCollection = new GameCollectionManager();
        GameDocumentManager gameDocument = new GameDocumentManager(gameCollection.getGameDocument(1));
        List<Document> gameList = new ArrayList<Document>();
        Map<String, Object> model = new HashMap<>();


        gameCollection.getCollection().find().iterator().forEachRemaining(game -> gameList.add(game));

            model.put("GAMES",  gameList);

        // The sources.HTML files are located under the resources directory
//        return new ModelAndView(main.model, main.viewutil.Path.Template.INDEX);
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };
//
//    public ShopController getBookByIsbn(String isbn) {
//        return shopPage(b -> b.getShopPage(isbn));
//    }

}
