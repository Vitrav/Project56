package controller;

import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import user.UserController;
import viewutil.Path;
import model.document.UserDocumentManager;
import org.bson.Document;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static viewutil.RequestUtil.getSessionCurrentUser;

public class ShopController {

    public static Route shopPage = (Request request, Response response) -> {
        GameCollectionManager gameCollection = new GameCollectionManager();
        List<GameDocumentManager> docManagers = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();

        UserCollectionManager userCollectionManager = new UserCollectionManager();
        if (userCollectionManager.getUserDocument(getSessionCurrentUser(request)) != null) {
            UserDocumentManager userDocumentManager = new UserDocumentManager(userCollectionManager.getUserDocument(getSessionCurrentUser(request)));
            if (userDocumentManager.isAdmin()) {
                model.put("userIsAdmin", true);
            }
        }

        gameCollection.getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));
        model.put("games", docManagers);
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

    public static Route gameToCart = (Request request, Response response) -> {
        UserController controller = new UserController(getSessionCurrentUser(request));
        GameCollectionManager gameCollection = new GameCollectionManager();
        List<GameDocumentManager> docManagers = new ArrayList<>();
        gameCollection.getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));

        Map<String, Object> model = new HashMap<>();

        UserCollectionManager userCollectionManager = new UserCollectionManager();
        UserDocumentManager userDocumentManager = new UserDocumentManager(userCollectionManager.getUserDocument(getSessionCurrentUser(request)));
        if (userDocumentManager.isAdmin()){
            model.put("userIsAdmin", true);
        }

        String gameID = request.queryParams().iterator().next();
        int actualGameID = Integer.parseInt(gameID);
        if (!controller.userHasGame(actualGameID)) {
            userDocumentManager.addCartItem(actualGameID);
        }

        model.put("games", docManagers);
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

    private static GameDocumentManager getGameDocManager(int gameId) {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId));
    }

}
