package controller;

import controller.utils.ConUtil;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import org.bson.Document;
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

        if (model.containsKey("games"))
            return ViewUtil.render(request, model, Path.Template.SHOP);
        addCurrentGame(request, model);
        addGames(model, getGameDocManager(request, model));


        //Add game to wishlist or cart.
        if (request.queryParams().iterator().hasNext()) {
            if (request.session().attribute("currentUser") == null) {
                if (request.queryParams().iterator().next().equalsIgnoreCase("wishlist")) {
                    model.put("notLoggedIn", true);
                    return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
                }
                String id = request.session().id();
                List<Document> items = ShopController.getUserItems().get(id);
                items.add(new UserCollectionManager().createCartItemDocument(getGameDocManager(request, model).getId()));
                ShopController.getUserItems().put(id, items);
                return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
            }
            if (request.queryParams().iterator().next().equalsIgnoreCase("wishlist")) {
                if (ConUtil.getUser(request).hasWishListItem(getGameDocManager(request, model).getId())) {
                    model.put("hasItem", true);
                    return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
                }
                ConUtil.getUser(request).addWishItem(getGameDocManager(request, model).getId());
                model.put("addedToList", true);
            } else if(request.queryParams().iterator().next().equalsIgnoreCase("cart")) {
                ConUtil.getUser(request).addCartItem(getGameDocManager(request, model).getId());
                model.put("addedToCart", true);
            }
            else if(request.queryParams().iterator().next().equalsIgnoreCase("cart")) {
                ConUtil.getUser(request).addCartItem(getGameDocManager(request, model).getId());
                model.put("addedToHistoryList", true);
            }
        }
        return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
    };

    private static void addGames(Map<String, Object> model, GameDocumentManager currentGame) {
        List<GameDocumentManager> docManagers = new ArrayList<>();
        new GameCollectionManager().getCollection().find().iterator().forEachRemaining(game -> {
            GameDocumentManager gm = ConUtil.getGameDocManager(game.getInteger("id"));
            if (currentGame.getGenre().equals(gm.getGenre()))
                docManagers.add(gm);
        });
        model.put("games", docManagers);
    }

    private static void addCurrentGame(Request request, Map<String, Object> model) {
        model.put("currentGame", getGameDocManager(request, model));
    }

    private static GameDocumentManager getGameDocManager(Request request, Map<String, Object> model) {
        //creates the url per game
        String id = request.url().substring(request.url().indexOf("single-page/"), request.url().length() - 1).replace("single-page/", "");
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(Integer.parseInt(id)));
    }

}
