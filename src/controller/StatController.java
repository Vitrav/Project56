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
        insertUsersToModel(model, users);
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

    private static void insertUsersToModel(Map<String, Object> model, List<UserDocumentManager> userManagers) {
        List<Integer> allAgeAmounts = new ArrayList<>();
        List<String> allAgeGroups = new ArrayList<>();

        int babyCount = 0;
        int kidCount = 0;
        int teenCount = 0;
        int adolescentCount = 0;
        int adultCount = 0;
        int greyCount = 0;

        for (UserDocumentManager user: userManagers) {
            if (user.getAge() <= 8){
                babyCount += 1;
            } else if (user.getAge() > 8 && user.getAge() <= 12 ) {
                kidCount += 1;
            } else if (user.getAge() > 12 && user.getAge() <= 17) {
                teenCount += 1;
            } else if (user.getAge() > 17 && user.getAge() <= 21) {
                adolescentCount += 1;
            } else if (user.getAge() > 21 && user.getAge() <= 40){
                adultCount += 1;
            } else {
                greyCount += 1;
            }
        }
        allAgeAmounts.add(babyCount);
        allAgeAmounts.add(kidCount);
        allAgeAmounts.add(teenCount);
        allAgeAmounts.add(adolescentCount);
        allAgeAmounts.add(adultCount);
        allAgeAmounts.add(greyCount);

        allAgeGroups.add("0 - 8");
        allAgeGroups.add("9 - 12");
        allAgeGroups.add("13 - 17");
        allAgeGroups.add("18 - 21");
        allAgeGroups.add("22 - 40");
        allAgeGroups.add("41 ->");

        model.put("allAgeAmounts", allAgeAmounts);
        model.put("allAgeGroups", allAgeGroups);
    }
}