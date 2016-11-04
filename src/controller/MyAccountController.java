package controller;

import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import spark.Request;
import spark.Response;
import spark.Route;
import user.UserController;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.getEmail;
import static viewutil.RequestUtil.getQueryUsername;
import static viewutil.RequestUtil.getSessionCurrentUser;

public class MyAccountController {

    public static Route accountPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserCollectionManager userCollectionManager = new UserCollectionManager();
        UserDocumentManager userDocumentManager = new UserDocumentManager(userCollectionManager.getUserDocument(getSessionCurrentUser(request)));

        model.put("userInfo", userDocumentManager);
        return ViewUtil.render(request, model, Path.Template.MYACCOUNT);
    };
}
