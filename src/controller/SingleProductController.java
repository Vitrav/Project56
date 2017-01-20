package controller;

import controller.utils.ConUtil;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import user.UserController;
import viewutil.Path;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static viewutil.RequestUtil.getSessionCurrentUser;


public class SingleProductController {

    public static Route singlePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        ConUtil.searchGame(request, model);
        ConUtil.addModelVariables(request, model);

        if (model.containsKey("games"))
            return ViewUtil.render(request, model, Path.Template.SHOP);
        addCurrentGame(request, model);
        addGames(model);

        //Add game to wishlist.
        if (request.queryParams().iterator().hasNext() && request.queryParams().iterator().next().equalsIgnoreCase("wishlist")) {
            System.out.println(request.queryParams().iterator().next());
            if (request.session().attribute("currentUser") == null) {
                model.put("notLoggedIn", true);
                return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
            }
            ConUtil.getUser(request).addWishItem(getGameDocManager(request, model).getId());
            model.put("addedToList", true);
        }

        if (request.queryParams().iterator().hasNext() && request.queryParams().iterator().next().equalsIgnoreCase("cart")){
            System.out.println(request.queryParams().iterator().next());
            if (request.session().attribute("currentUser") == null) {
                model.put("notLoggedIn", true);
                return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
            }
            ConUtil.getUser(request).addCartItem(getGameDocManager(request, model).getId());
            model.put("addedToList", true);
        }

        return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
    };

    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private static void addGames(Map<String, Object> model) {
        List<GameDocumentManager> docManagers = new ArrayList<>();
        new GameCollectionManager().getCollection().find().iterator().forEachRemaining(game -> docManagers.add(getGameDocManager(game.getInteger("id"))));
        model.put("games", docManagers);
    }

    private static void addCurrentGame(Request request, Map<String, Object> model) {
        model.put("currentGame", getGameDocManager(request, model));
    }

    private static GameDocumentManager getGameDocManager(Request request, Map<String, Object> model) {
        String id = request.url().substring(request.url().indexOf("single-page/"), request.url().length() - 1).replace("single-page/", "");
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(Integer.parseInt(id)));
    }

    private static GameDocumentManager getGameDocManager(Integer gameId) {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId));
    }

}
