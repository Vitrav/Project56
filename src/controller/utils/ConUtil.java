package controller.utils;

import model.Database;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import spark.Request;
import user.UserController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static viewutil.RequestUtil.getSessionCurrentUser;

public class ConUtil {

    public static void searchGame(Request request,  Map<String, Object> model) {
        if (request.queryParams().iterator().hasNext() && request.queryParams().iterator().next().equalsIgnoreCase("search")) {
            String product = request.queryParams("search").replaceAll(" ", "");
            List<GameDocumentManager> docManagers = new ArrayList<>();

            new GameCollectionManager().getCollection().find().iterator().forEachRemaining(game -> {
                GameDocumentManager docManager = getGameDocManager(game.getInteger("id"));
                if (docManager.getName().toLowerCase().replaceAll(" ", "").contains(product.toLowerCase())
                        || docManager.getPlatform().equalsIgnoreCase(product) || docManager.getGenre().replaceAll(" ", "").equalsIgnoreCase(product)
                        || docManager.getPublisher().replaceAll(" ", "").equalsIgnoreCase(product))
                    docManagers.add(docManager);
            });
            model.put("games", docManagers);
            if (docManagers.isEmpty())
                model.put("notFound", true);
        }
    }

    public static UserDocumentManager getUser(Request request) {
        return new UserDocumentManager(new UserCollectionManager().getUserDocument(request.session().attribute("currentUser")));
    }

    public static void addToCart(Request request) {
        if (request.queryParams().iterator().hasNext()) {
            int actualGameID = getGameId(request);
            if (actualGameID == -1)
                return;
            UserDocumentManager manager = new UserDocumentManager(new UserCollectionManager().getUserDocument(getSessionCurrentUser(request)));
            if (!new UserController(getSessionCurrentUser(request)).userHasGame(actualGameID))
                manager.addCartItem(actualGameID);
            else
                manager.incGameAmount(actualGameID);
        }
    }

    private static int getGameId(Request request) {
        for (String param : request.queryParams())
            if (isInt(param))
                return Integer.parseInt(param);
        return -1;
    }

    private static boolean isInt (String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void insertAllUsers(Request request, Map<String, Object> model) {
        List<UserDocumentManager> users = new ArrayList<>();
        Database.getInstance().getUserCollection().find().iterator().forEachRemaining(user -> users.add(new UserDocumentManager(user)));
        model.put("allUserManagers", users);
        request.session().attribute("allUserManagers", users);
    }

    public static void addGames(Map<String, Object> model) {
        addGames(model, 0, Integer.MAX_VALUE);
    }

    public static void addGames(Map<String, Object> model, int min, int max) {
        List<GameDocumentManager> docManagers = new ArrayList<>();
        new GameCollectionManager().getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));
        model.put("games", docManagers.stream().filter(doc ->doc.getPrice() > min && doc.getPrice() < max).collect(Collectors.toList()));
    }

    public static void addAllGames(Map<String, Object> model) {
        List<GameDocumentManager> docManagers = new ArrayList<>();
        new GameCollectionManager().getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));
        model.put("allGames", docManagers);
    }

    public static void insertGameManager(Map<String, Object> model, List<Integer> managerList) {
        List<GameDocumentManager> gameManagers = new ArrayList<>();
        managerList.forEach(id -> gameManagers.add(ConUtil.getGameDocManager(id)));
        model.put("gameManagers", gameManagers);
    }

    public static GameDocumentManager getGameDocManager(int gameId) {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId));
    }

    public static UserDocumentManager getUserDocManager(String user) {
        return new UserDocumentManager(new UserCollectionManager().getUserDocument(user));
    }


}
