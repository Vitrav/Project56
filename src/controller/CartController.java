package controller;

import controller.utils.ConUtil;
import model.document.CartItemDocumentManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import org.bson.Document;
import viewutil.Path;
import viewutil.RequestUtil;
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
        infoToPage(model, request);
        return ViewUtil.render(request, model, viewutil.Path.Template.CART);
    };

    public static Route handleCartPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String buttonName = request.queryParams(request.queryParams().iterator().next());

        if (buttonName.equalsIgnoreCase("remove")) {
            int gameId = Integer.parseInt(request.queryParams().iterator().next());
            if (RequestUtil.getSessionCurrentUser(request) == null)
                ShopController.removeUserItem(request.session().id(),gameId);
            else
                ConUtil.getUser(request).removeCartItem(gameId);
        } else if (buttonName.contains("Proceed to Checkout")) {
            if (RequestUtil.getSessionCurrentUser(request) == null)
                response.redirect(Path.Web.LOGIN);
            infoToPage(model, request);
            if (ConUtil.getUser(request).getCartItems().isEmpty())
                return ViewUtil.render(request, model, Path.Template.CART);

            for (Document item : ConUtil.getUser(request).getCartItems()) {
                if (new GameDocumentManager(item.getInteger("id")).getAmountInStock() <  new CartItemDocumentManager(item).getAmount()) {
                    model.put("outOfStock", true);
                    model.put("outOfStockItem", new GameDocumentManager(item.getInteger("id")));
                    return ViewUtil.render(request, model, Path.Template.CART);
                }
            }
            ConUtil.getUser(request).getCartItems().forEach(item -> {
                GameDocumentManager manager = new GameDocumentManager(item.getInteger("id"));
                manager.setAmountInStock(manager.getAmountInStock() - new CartItemDocumentManager(item).getAmount());
            });

            ConUtil.getUser(request).getCartItems().forEach(item -> ConUtil.getUser(request).addHistoryItem(item.getInteger("id"), item.getInteger("amount")));

            //Remove the cart items after a delay.
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ConUtil.getUser(request).setCartItems(new ArrayList<>());
                    }
                }, 1200
            );
            return ViewUtil.render(request, model, Path.Template.PURCHASESUCCESSFUL);
        }

        infoToPage(model, request);
        return ViewUtil.render(request, model, Path.Template.CART);
    };

    private static void infoToPage(Map<String, Object> model, Request request) {
        List<Document> cartItems = RequestUtil.getSessionCurrentUser(request) != null ? ConUtil.getUser(request).getCartItems() : ShopController.getUserItems().get(request.session().id());
        List<GameDocumentManager> cartGameList = new ArrayList<>();
        cartItems.iterator().forEachRemaining(game -> cartGameList.add(ConUtil.getGameDocManager(game.getInteger("id"))));
        model.put("cartGamesInfo", cartGameList);
    }
}
