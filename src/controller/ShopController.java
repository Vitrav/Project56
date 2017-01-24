package controller;

import controller.utils.ConUtil;
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
        Map<String, Object> model = new HashMap<>();
        addGames(request, model);
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

    public static Route shopPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        if (getGameId(request) != -1) {
            ConUtil.addToCart(request);
            addGames(request, model);
        } else if (request.queryParams("search") != null && request.queryParams("search").length() > 1) {
            ConUtil.searchGame(request, model);
            model.put("searched", true);
        } else {
            addGames(request, model);
            model.put("hasValues", true);
            model.put("min", Integer.parseInt(request.queryParams("min")));
            model.put("max", Integer.parseInt(request.queryParams("max")));
        }
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

    private static int getGameId(Request request) {
        for (String param : request.queryParams())
            if (request.queryParams(param).equalsIgnoreCase("add to cart"))
                return Integer.parseInt(param);
        return -1;
    }

    private static void addGames(Request request, Map<String, Object> model) {
        if (request.queryParams("min") != null && request.queryParams("max") != null)
            ConUtil.addGames(model, Integer.parseInt(request.queryParams("min")), Integer.parseInt(request.queryParams("max")));
        else
            ConUtil.addGames(model);
    }

}
