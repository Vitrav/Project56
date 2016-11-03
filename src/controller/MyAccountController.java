package controller;

import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class MyAccountController {

    public static Route accountPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.MYACCOUNT);
    };

}
