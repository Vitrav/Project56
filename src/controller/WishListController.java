package controller;

import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static viewutil.RequestUtil.getSessionCurrentUser;

public class WishListController {

    public static Route wishlistPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserDocumentManager manager = getUserDocManager(getSessionCurrentUser(request));

        insertgameManager(model, manager);
        model.put("userInfo", manager);
        return ViewUtil.render(request, model, Path.Template.WISHLIST);
    };

    public static Route handleWishlistPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserDocumentManager manager = getUserDocManager(getSessionCurrentUser(request));
        manager.setWishList(manager.wishListIsPrivate() ? false : true);
        if (manager.getWishList().isEmpty())
            manager.addWishItem(1);

        insertgameManager(model, manager);
        model.put("userInfo", getUserDocManager(getSessionCurrentUser(request)));
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
