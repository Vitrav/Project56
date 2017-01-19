package controller.utils;

import model.Database;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import spark.Request;
import user.UserController;
import viewutil.RequestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static viewutil.RequestUtil.getQueryUsername;
import static viewutil.RequestUtil.getSessionCurrentUser;

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
                    if (docManager.getName().toLowerCase().replaceAll(" ", "").contains(product.toLowerCase()) || docManager.getPlatform().equalsIgnoreCase(product))
                        docManagers.add(docManager);
                });
                if (!docManagers.isEmpty()) {
                    model.put("games", docManagers);
                } else
                    model.put("notFound", true);
            }
        }
    }

    public static void addModelVariables(Request request, Map<String, Object> model) {
        if (request.session().attribute("currentUser") == null)
            return;
        model.put("authenticationSucceeded", true);
        model.put("userDocumentManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(request.session().attribute("currentUser"))));
        model.put("hasManager", true);
        addAdmin(request, model);
    }

    private static void addAdmin(Request request, Map<String, Object> model) {
        UserCollectionManager userCollectionManager = new UserCollectionManager();
        if (userCollectionManager.getUserDocument(getSessionCurrentUser(request)) != null) {
            UserDocumentManager userDocumentManager = new UserDocumentManager(userCollectionManager.getUserDocument(getSessionCurrentUser(request)));
            if (userDocumentManager.isAdmin())
                model.put("userIsAdmin", true);
        }
    }

    public static void addToCart(Request request) {
        if (request.queryParams().iterator().hasNext()) {
            int actualGameID = Integer.parseInt(request.queryParams().iterator().next());
            UserDocumentManager manager = new UserDocumentManager(new UserCollectionManager().getUserDocument(getSessionCurrentUser(request)));
            if (!new UserController(getSessionCurrentUser(request)).userHasGame(actualGameID))
                manager.addCartItem(actualGameID);
            else
                manager.incGameAmount(actualGameID);
        }
    }

    public static void insertAllUsers(Request request, Map<String, Object> model) {
        List<UserDocumentManager> users = new ArrayList<>();
        Database.getInstance().getUserCollection().find().iterator().forEachRemaining(user -> users.add(new UserDocumentManager(user)));

        ConUtil.addAdmin(request, model);
        model.put("allUserManagers", users);
        request.session().attribute("allUserManagers", users);
    }

    public static void addGames(Map<String, Object> model) {
        List<GameDocumentManager> docManagers = new ArrayList<>();
        new GameCollectionManager().getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));
        model.put("games", docManagers);
    }

    public static GameDocumentManager getGameDocManager(int gameId) {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId));
    }


}
