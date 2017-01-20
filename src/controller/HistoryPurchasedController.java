package controller;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static viewutil.RequestUtil.getSessionCurrentUser;

/**
 * Created by eigenaar on 17-1-2017.
 */
public class HistoryPurchasedController {

   /* public static Route HistoryPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserDocumentManager manager = getUserDocManager(getSessionCurrentUser(request));

        insertgameManager(model, manager);
        model.put("userInfo", manager);
        return ViewUtil.render(request, model, Path.Template.HISTORY);
    };

    public static Route handleHistoryPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserDocumentManager manager = getUserDocManager(getSessionCurrentUser(request));


        //Add games to wishlist.
        Database.getInstance().getGameCollection().find().iterator().forEachRemaining(game -> manager.addHistoryItem(game.getInteger("id")));

        insertgameManager(model, manager);
        model.put("userInfo", getUserDocManager(getSessionCurrentUser(request)));
        return ViewUtil.render(request, model, Path.Template.HISTORY);
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
    }*/
}
