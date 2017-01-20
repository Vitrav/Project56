package controller;

import controller.utils.ConUtil;
import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.getSessionCurrentUser;

public class MyAccountController {

    public static Route accountPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        ConUtil.addModelVariables(request, model);
        return ViewUtil.render(request, model, Path.Template.MYACCOUNT);
    };
}
