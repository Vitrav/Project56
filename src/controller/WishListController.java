package controller;

import controller.utils.ConUtil;
import model.Database;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.DocumentManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.*;

import static viewutil.RequestUtil.getSessionCurrentUser;

public class WishListController {

    public static Route wishListPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserDocumentManager manager = ConUtil.getUserDocManager(getSessionCurrentUser(request));

        insertGameManager(model, manager);
        ConUtil.addModelVariables(request, model);
        return ViewUtil.render(request, model, Path.Template.WISHLIST);
    };

    public static Route handleWishListPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String buttonName = request.queryParams(request.queryParams().iterator().next());
        UserDocumentManager manager = ConUtil.getUserDocManager(getSessionCurrentUser(request));
        ConUtil.addModelVariables(request, model);

        if (buttonName.toLowerCase().contains("set your wishlist"))
            manager.setWishList(manager.wishListIsPrivate() ? false : true);
        else if (buttonName.equalsIgnoreCase("remove"))
            manager.removeWishItem(Integer.parseInt(request.queryParams().iterator().next()));
        else if (buttonName.equalsIgnoreCase("View public wishlists")) {
            List<DocumentManager> publicPlayers = new ArrayList<>();
            Database.getInstance().getUserCollection().find().iterator().forEachRemaining(u -> {
                UserDocumentManager m = new UserDocumentManager(u);
                if (!m.wishListIsPrivate() && m.getWishList().size() > 0)
                    publicPlayers.add(m);
            });
            model.put("publicPlayers", publicPlayers);
            model.put("viewList", true);
            return ViewUtil.render(request, model, Path.Template.WISHLIST);
        } else if (buttonName.equalsIgnoreCase("view")) {
            insertGameManager(model, ConUtil.getUserDocManager(request.queryParams().iterator().next()));
            model.put("view", true);
            model.put("userInfo", ConUtil.getUserDocManager(request.queryParams().iterator().next()));
            return ViewUtil.render(request, model, Path.Template.WISHLIST);
        }
        model.put("userDocumentManager", ConUtil.getUserDocManager(getSessionCurrentUser(request)));
        insertGameManager(model, manager);
        return ViewUtil.render(request, model, Path.Template.WISHLIST);
    };

    private static void insertGameManager(Map<String, Object> model, UserDocumentManager manager) {
        List<GameDocumentManager> gameManagers = new ArrayList<>();
        manager.getWishList().forEach(id -> gameManagers.add(ConUtil.getGameDocManager(id)));
        model.put("gameManagers", gameManagers);
    }

}
