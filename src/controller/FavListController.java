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
        ConUtil.addModelVariables(request, model);
        return ViewUtil.render(request, model, Path.Template.FAVORITELIST);
    };
}
