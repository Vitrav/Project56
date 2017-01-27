package controller;

import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class MyAccountController {

    public static Route accountPage = (Request request, Response response) ->
        ViewUtil.render(request, new HashMap<>(), Path.Template.MYACCOUNT);

}
