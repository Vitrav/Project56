package controller;


import controller.utils.ConUtil;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;


public class IndexController {

    public static Route indexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        ConUtil.addModelVariables(request, model);
        return ViewUtil.render(request, model, viewutil.Path.Template.INDEX);
    };

}
