package controller;

import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eigenaar on 18-1-2017.
 */
public class PurchasedSuccesful {
    public static Route purchasedsuccesful = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, viewutil.Path.Template.PURCHASEDSUCCESFUL);
    };

}
