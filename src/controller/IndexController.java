package controller;

import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;


public class IndexController {

    public static Route indexPage = (Request request, Response response) ->
        ViewUtil.render(request, new HashMap<>(), viewutil.Path.Template.INDEX);

}
