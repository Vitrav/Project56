package controller;


import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;


public class CartController {

    public static Route cartPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        // The sources.HTML files are located under the resources directory
        return ViewUtil.render(request, model, viewutil.Path.Template.CART);
    };

}
