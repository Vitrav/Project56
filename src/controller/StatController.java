package controller;

import controller.utils.ConUtil;
import model.Database;
import model.document.HistoryDocumentManager;
import model.document.UserDocumentManager;
import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.*;

public class StatController {

    public static Route statPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        ConUtil.searchGame(request, model);
        ConUtil.insertAllUsers(request, model);

        if (model.containsKey("games"))
            return ViewUtil.render(request, model, Path.Template.STATISTICS);

        ConUtil.addGames(model);
        createAllHisManagers(model);
        return ViewUtil.render(request, model, Path.Template.STATISTICS);
    };

    private static void createAllHisManagers(Map<String, Object> model) {
        List<UserDocumentManager> users = new ArrayList<>();
        Database.getInstance().getUserCollection().find().iterator().forEachRemaining(user -> users.add(new UserDocumentManager(user)));

        List<HistoryDocumentManager> hisManagers = new ArrayList<>();
        users.forEach(user -> user.getPurchaseHistory().forEach(h -> hisManagers.add(new HistoryDocumentManager(h))));

        insertHisManagersToModel(model, hisManagers);
    }

    private static void insertHisManagersToModel(Map<String, Object> model, List<HistoryDocumentManager> hisManagers) {
        List<String> allDates = new ArrayList<>();
        List<Integer> allCounts = new ArrayList<>();

        for (HistoryDocumentManager h : hisManagers){
            if (!allDates.contains(h.getDate()))
                allDates.add(h.getDate());
        }

        for (String date: allDates) {
            int count = 0;
            for (HistoryDocumentManager h : hisManagers) {
                if (h.getDate().equals(date))
                    count += h.getAmount();
            }
            allCounts.add(count);
        }

        model.put("allDates", allDates);
        model.put("allCounts", allCounts);
    }
}