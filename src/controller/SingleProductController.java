package controller;

import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import viewutil.Path;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SingleProductController {

    public static Route singlePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        addGames(model);
        addCurrentGame(request, model);

        if (request.queryParams().iterator().hasNext()) {
            //Search button clicked.
            if (request.queryParams().iterator().next().equalsIgnoreCase("search")) {
                String product = request.queryParams("search").replaceAll(" ", "");

                List<GameDocumentManager> docManagers = new ArrayList<>();
                GameCollectionManager gameCollection = new GameCollectionManager();
                gameCollection.getCollection().find().iterator().forEachRemaining(game -> {
                    GameDocumentManager docManager = getGameDocManager(game.getInteger("id"));
                    if (docManager.getName().toLowerCase().replaceAll(" ", "").contains(product.toLowerCase()))
                        docManagers.add(docManager);
                });
                if (!docManagers.isEmpty()) {
                    model.put("games", docManagers);
                    return ViewUtil.render(request, model, Path.Template.SHOP);
                }
                model.put("notFound", true);
            }
        }
        return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
    };

    private static void addGames(Map<String, Object> model) {
        List<GameDocumentManager> docManagers = new ArrayList<>();
        new GameCollectionManager().getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));
        model.put("games", docManagers);
    }

    private static void addCurrentGame(Request request, Map<String, Object> model) {
        String id = request.url().substring(request.url().indexOf("single-page/"), request.url().length() - 1).replace("single-page/", "");
        model.put("currentGame", new GameDocumentManager(new GameCollectionManager().getGameDocument(Integer.parseInt(id))));
    }

    private static GameDocumentManager getGameDocManager(Integer gameId) {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId));
    }

}
