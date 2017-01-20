package controller.utils;

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
 * Created by eigenaar on 20-1-2017.
 */
public class MyHistoryListController {

    public static Route myhistoryListPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserDocumentManager manager = ConUtil.getUserDocManager(getSessionCurrentUser(request));

        insertGameManager(model, manager);
        ConUtil.addModelVariables(request, model);
        return ViewUtil.render(request, model, Path.Template.MYHISTORYLIST);
    };




    private static void insertGameManager(Map<String, Object> model, UserDocumentManager manager) {
        List<GameDocumentManager> gameManagers = new ArrayList<>();
        manager.getPurchaseHistory().forEach(id -> gameManagers.add(ConUtil.getGameDocManager(id)));
        model.put("gameManagers", gameManagers);
    }
}
