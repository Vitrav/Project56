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
        ConUtil.addModelVariables(request, model);
        ConUtil.searchGame(request, model);

        if (model.containsKey("games"))
            return ViewUtil.render(request, model, Path.Template.SHOP);
        ConUtil.addGames(model);
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

    public static Route gameToCart = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        ConUtil.addGames(model);
        ConUtil.addToCart(request);
        ConUtil.addModelVariables(request, model);
        return ViewUtil.render(request, model, viewutil.Path.Template.SHOP);
    };

}
