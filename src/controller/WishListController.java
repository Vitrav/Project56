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
        ConUtil.insertGameManager(model, ConUtil.getUserDocManager(getSessionCurrentUser(request)).getWishList());
        return ViewUtil.render(request, model, Path.Template.WISHLIST);
    };

    public static Route handleWishListPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String buttonName = request.queryParams(request.queryParams().iterator().next());
        UserDocumentManager manager = ConUtil.getUser(request);

        //looks for the pressed button and executes the appropriate action
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
        //view all other public wish lists
        } else if (buttonName.equalsIgnoreCase("view")) {
            insertPlayerGameManager(model, ConUtil.getUserDocManager(request.queryParams().iterator().next()).getWishList());
            model.put("view", true);
            model.put("userInfo", ConUtil.getUserDocManager(request.queryParams().iterator().next()));
        }
        ConUtil.insertGameManager(model, manager.getWishList());
        return ViewUtil.render(request, model, Path.Template.WISHLIST);
    };

    private static void insertPlayerGameManager(Map<String, Object> model, List<Integer> managerList) {
        List<GameDocumentManager> gameManagers = new ArrayList<>();
        managerList.forEach(id -> gameManagers.add(ConUtil.getGameDocManager(id)));
        model.put("pGameManagers", gameManagers);
    }

}
