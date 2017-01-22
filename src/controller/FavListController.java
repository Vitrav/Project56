package controller;

import controller.utils.ConUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class FavListController {

    public static Route favListPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if (request.queryParams().iterator().hasNext() && request.queryParams(request.queryParams().iterator().next()).equalsIgnoreCase("remove"))
            ConUtil.getUser(request).removeFavItem(Integer.parseInt(request.queryParams().iterator().next()));
        ConUtil.insertGameManager(model, ConUtil.getUser(request).getFavouriteList());
        return ViewUtil.render(request, model, Path.Template.FAVORITELIST);
    };

}
