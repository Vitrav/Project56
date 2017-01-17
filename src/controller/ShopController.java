package controller;

import model.collection.GameCollectionManager;
import model.document.GameDocumentManager;
import viewutil.Path;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopController {

    public static Route shopPage = (Request request, Response response) -> {
        GameCollectionManager gameCollection = new GameCollectionManager();
        List<GameDocumentManager> docManagers = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();

        gameCollection.getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));
        model.put("games", docManagers);
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

    public static Route shopPost = (Request request, Response response) -> {
        System.out.println("test");
        HashMap<String, Object> model = new HashMap<>();
        System.out.println((request.url()));

        model.put("book", model);
//            return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);

//        if (clientAcceptsJson(request)) {
//            return dataToJson(bookDao.getBookByIsbn(getParamIsbn(request)));
//        }
        return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
    };

//
//    public ShopController getBookByIsbn(String isbn) {
//        return shopPage(b -> b.getShopPage(isbn));
//    }

    private static GameDocumentManager getGameDocManager(int gameId) {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId));
    }

}
