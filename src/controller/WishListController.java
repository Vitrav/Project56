package controller;

import controller.utils.ConUtil;
import model.Database;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
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
        UserDocumentManager manager = getUserDocManager(getSessionCurrentUser(request));

        insertgameManager(model, manager);
        ConUtil.addModelVariables(request, model);
        return ViewUtil.render(request, model, Path.Template.WISHLIST);
    };

    public static Route handleWishListPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String buttonName = request.queryParams(request.queryParams().iterator().next());
        UserDocumentManager manager = getUserDocManager(getSessionCurrentUser(request));
        ConUtil.addModelVariables(request, model);

        if (buttonName.toLowerCase().contains("set wishlist"))
            manager.setWishList(manager.wishListIsPrivate() ? false : true);
        else if (buttonName.equalsIgnoreCase("remove"))
            manager.removeWishItem(Integer.parseInt(request.queryParams().iterator().next()));

        manager = getUserDocManager(getSessionCurrentUser(request));
        insertgameManager(model, manager);
        return ViewUtil.render(request, model, Path.Template.WISHLIST);
    };

    private static UserDocumentManager getUserDocManager(String user) {
        return new UserDocumentManager(new UserCollectionManager().getUserDocument(user));
    }

    private static GameDocumentManager getGameDocManager(int gameId) {
        return new GameDocumentManager(new GameCollectionManager().getGameDocument(gameId));
    }

    private static void insertgameManager(Map<String, Object> model, UserDocumentManager manager) {
        List<GameDocumentManager> gameManagers = new ArrayList<>();
        manager.getWishList().forEach(id -> gameManagers.add(getGameDocManager(id)));
        model.put("gameManagers", gameManagers);
    }

}
