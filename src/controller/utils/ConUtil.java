package controller.utils;

import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import spark.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static viewutil.RequestUtil.getSessionCurrentUser;

/**
 * Created by Sivar on 19-1-2017.
 */
public class ConUtil {

    public static void searchGame(Request request,  Map<String, Object> model) {
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

                }
                model.put("notFound", true);
            }
        }
    }

    public static void addAdmin(Request request, Map<String, Object> model) {
        UserCollectionManager userCollectionManager = new UserCollectionManager();
        if (userCollectionManager.getUserDocument(getSessionCurrentUser(request)) != null) {
            UserDocumentManager userDocumentManager = new UserDocumentManager(userCollectionManager.getUserDocument(getSessionCurrentUser(request)));
            if (userDocumentManager.isAdmin()) {
                model.put("userIsAdmin", true);
            }
        }
    }

    public static void addGames(Map<String, Object> model) {
        List<GameDocumentManager> docManagers = new ArrayList<>();
        new GameCollectionManager().getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));
        model.put("games", docManagers);
    }

    private static GameDocumentManager getGameDocManager(int gameId) {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId));
    }


}
