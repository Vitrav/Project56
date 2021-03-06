package controller;

import controller.utils.ConUtil;
import model.Database;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.DocumentManager;
import model.document.GameDocumentManager;
import org.apache.commons.collections.map.HashedMap;
import user.UserController;
import viewutil.Path;
import model.document.UserDocumentManager;
import org.bson.Document;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static viewutil.RequestUtil.getSessionCurrentUser;

public class ShopController {

    //cart items until you're logged in.
    private static Map<String, List<Document>> userItems = new HashedMap();

    public static Route shopPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        addPageAmount(model);
        getGamesForPage(request, model);
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

    public static Route shopPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        addPageAmount(model);

        //looks if the game exists when clicking the add to cart button.
        if (getGameId(request) != -1) {
            //checks if the user has the game already added to the cart or not
            if (request.session().attribute("currentUser") != null) {
                ConUtil.addToCart(request);
            } else {
                final String id = request.session().id();
                final int gameId = getGameId(request);

                if (userHasGame(id, gameId)) {
                    incGameAmount(id, gameId);
                } else {
                    List<Document> items = userItems.get(id);
                    items.add(new UserCollectionManager().createCartItemDocument(gameId));
                    userItems.put(id, items);
                }
            }
            getGamesForPage(request, model);
        //search for a game
        } else if (request.queryParams("search") != null && request.queryParams("search").length() > 1) {
            ConUtil.searchGame(request, model);
            model.put("searched", true);
        } else {
            //price filter
            addGames(request, model);
            model.put("hasValues", true);
            model.put("min", Integer.parseInt(request.queryParams("min")));
            model.put("max", Integer.parseInt(request.queryParams("max")));
            model.put("searched", true);
        }
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

    public static boolean userHasGame(String userId, int gameId) {
        if (!userItems.containsKey(userId))
            return false;
        for (Document i : userItems.get(userId))
            if (i.getInteger("id") == gameId)
                return true;
        return false;
    }

    public static void incGameAmount(String userId, int gameId) {
        List<Document> items = userItems.get(userId);
        items.forEach(item -> {
            if (item.getInteger("id") == gameId)
                item.put("amount", item.getInteger("amount") + 1);
        });
        userItems.put(userId, items);
    }

    private static int getGameId(Request request) {
        for (String param : request.queryParams())
            if (request.queryParams(param).equalsIgnoreCase("add to cart"))
                return Integer.parseInt(param);
        return -1;
    }

    private static void addGames(Request request, Map<String, Object> model) {
        //adds games to the shop page based on the filter
        if (request.queryParams("min") != null && request.queryParams("max") != null)
            ConUtil.addGames(model, Integer.parseInt(request.queryParams("min")), Integer.parseInt(request.queryParams("max")));
        else
            ConUtil.addGames(model);
    }

    private static void getGamesForPage(Request request, Map<String, Object> model) {
        //displays a set amount of games per page
        int pageId = Integer.parseInt(request.url().replaceAll("/", "").replaceAll("http:localhost:4567shop", ""));
        List<GameDocumentManager> docManagers = new ArrayList<>();
        model.put("next", pageId == calcPageAmount() ? pageId : pageId + 1);
        model.put("prev", pageId == 1 ? 1 : pageId - 1);

        int count = 0;
        for (Document game : Database.getInstance().getGameCollection().find()) {
            if ((count / 8) + 1 == pageId)
                docManagers.add(new GameDocumentManager(game));
            count++;
        }
        model.put("games", docManagers);

    }

    private static void addPageAmount(Map<String, Object> model) {
        //adds another page when the limit per page has been reached
        List<Integer> pages = new ArrayList<>();
        int amount = calcPageAmount();
        while (amount > 0) {
            pages.add((calcPageAmount() - amount) + 1);
            amount -= 1;
        }
        model.put("pages", pages);
    }

    private static int calcPageAmount() {
        int amount = (int) Database.getInstance().getGameCollection().count() / 8;
        return Database.getInstance().getGameCollection().count() > amount * 8 ? amount + 1 : amount;
    }

    public static Map<String, List<Document>> getUserItems() {
        return userItems;
    }

    public static void removeUserItem(String id, int gameId) {
        List<Document> items = userItems.get(id);
        for (int i = 0; i < userItems.get(id).size(); i++) {
            if (userItems.get(id).get(i).getInteger("id") == gameId) {
                items.remove(i);
                break;
            }
        }
        userItems.put(id, items);
    }

}
