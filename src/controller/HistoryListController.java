package controller;

import controller.utils.ConUtil;
import model.document.GameDocumentManager;
import model.document.HistoryDocumentManager;
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

public class HistoryListController {

    public static Route historyListPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        insertHisManagers(request, model);
        return ViewUtil.render(request, model, Path.Template.HISTORYLIST);
    };

    public static Route historyPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        int gameId = Integer.parseInt(request.queryParams().iterator().next());
        model.put("currentGame", new GameDocumentManager(gameId));

        //looks which button is button is pressed
        if (request.queryParams(request.queryParams().iterator().next()).contains("Add to fav list")) {
            ConUtil.getUser(request).addFavouriteItem(gameId);
            model.put("itemAdded", true);
        } else if (request.queryParams(request.queryParams().iterator().next()).contains("Remove from fav list")) {
            ConUtil.getUser(request).removeFavItem(gameId);
            model.put("itemRemoved", true);
        }
        insertHisManagers(request, model);
        return ViewUtil.render(request, model, Path.Template.HISTORYLIST);
    };

    private static void insertHisManagers(Request request, Map<String, Object> model) {
        //insert all purchased games to the history list
        List<HistoryDocumentManager> hisManagers = new ArrayList<>();
        ConUtil.getUser(request).getPurchaseHistory().forEach(h -> hisManagers.add(new HistoryDocumentManager(h)));
        model.put("hisManagers", hisManagers);
    }

}
