package controller;

import controller.utils.ConUtil;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import viewutil.Path;
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
        infoToPage(model, ConUtil.getUser(request));
        return ViewUtil.render(request, model, viewutil.Path.Template.CART);
    };

    public static Route handleCartPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String buttonName = request.queryParams(request.queryParams().iterator().next());

        if (buttonName.equalsIgnoreCase("remove")) {
            ConUtil.getUser(request).removeCartItem(Integer.parseInt(request.queryParams().iterator().next()));
        } else if (buttonName.contains("Proceed to Checkout")) {
            infoToPage(model, ConUtil.getUser(request));
            ConUtil.getUser(request).getCartItems().forEach(item -> ConUtil.getUser(request).addHistoryItem(item.getInteger("id"), item.getInteger("amount")));
            ConUtil.getUser(request).setCartItems(new ArrayList<>());
            return ViewUtil.render(request, model, Path.Template.PURCHASESUCCESSFUL);
        }

        infoToPage(model, ConUtil.getUser(request));
        return ViewUtil.render(request, model, Path.Template.CART);
    };

    private static void infoToPage(Map<String, Object> model, UserDocumentManager manager) {
        List<GameDocumentManager> cartGameList = new ArrayList<>();
        List<Integer> games = new ArrayList<>();

        manager.getCartItems().iterator().forEachRemaining(game -> games.add(game.getInteger("id")));
        games.forEach(id -> cartGameList.add(ConUtil.getGameDocManager(id)));
        model.put("cartGamesInfo", cartGameList);
    }
}
