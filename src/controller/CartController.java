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
        //stores the id of the button you pressed
        String buttonName = request.queryParams(request.queryParams().iterator().next());

        //looks for the id of the button you pressed
        if (buttonName.equalsIgnoreCase("remove")) {
            int gameId = Integer.parseInt(request.queryParams().iterator().next());
            //looks if you're logged in or not
            if (RequestUtil.getSessionCurrentUser(request) == null)
                ShopController.removeUserItem(request.session().id(),gameId);
            else
                ConUtil.getUser(request).removeCartItem(gameId);
        } else if (buttonName.contains("Proceed to Checkout")) {
            //requires you to log in
            if (RequestUtil.getSessionCurrentUser(request) == null)
                response.redirect(Path.Web.LOGIN);
            infoToPage(model, request);
            //cart is empty
            if (ConUtil.getUser(request).getCartItems().isEmpty())
                return ViewUtil.render(request, model, Path.Template.CART);

            //looks if the games you want are still in stock
            for (Document item : ConUtil.getUser(request).getCartItems()) {
                if (new GameDocumentManager(item.getInteger("id")).getAmountInStock() <  new CartItemDocumentManager(item).getAmount()) {
                    model.put("outOfStock", true);
                    model.put("outOfStockItem", new GameDocumentManager(item.getInteger("id")));
                    return ViewUtil.render(request, model, Path.Template.CART);
                }
            }
            //setts new amount in stock
            ConUtil.getUser(request).getCartItems().forEach(item -> {
                GameDocumentManager manager = new GameDocumentManager(item.getInteger("id"));
                manager.setAmountInStock(manager.getAmountInStock() - new CartItemDocumentManager(item).getAmount());
            });
            //adds the purchased items to your history list
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
        //putts information useful for the cart in the model
        List<Document> cartItems = RequestUtil.getSessionCurrentUser(request) != null ? ConUtil.getUser(request).getCartItems() : ShopController.getUserItems().get(request.session().id());
        List<GameDocumentManager> cartGameList = new ArrayList<>();
        cartItems.iterator().forEachRemaining(game -> cartGameList.add(ConUtil.getGameDocManager(game.getInteger("id"))));
        model.put("cartGamesInfo", cartGameList);
    }
}
