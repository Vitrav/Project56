package controller;

import controller.utils.ConUtil;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartController {

    public static Route cartPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserDocumentManager userDocumentManager = ConUtil.getUser(request);
        List<GameDocumentManager> cartGameList = new ArrayList<>();

        List<Integer> games = new ArrayList<Integer>();
        userDocumentManager.getCartItems().iterator().forEachRemaining(game -> games.add(game.getInteger("id")));

        games.forEach(id -> cartGameList.add(ConUtil.getGameDocManager(id)));
        model.put("cartGamesInfo", cartGameList);
        model.put("userManager", userDocumentManager);
        ConUtil.addModelVariables(request, model);

        if (request.queryParams().iterator().hasNext() && (request.queryParams().iterator().next().equalsIgnoreCase("remove"))) {
            System.out.println("yeeeeee");
        }

        return ViewUtil.render(request, model, viewutil.Path.Template.CART);
    };
}
