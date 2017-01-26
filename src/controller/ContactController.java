package controller;

import spark.Request;
import spark.Response;
import spark.Route;
import user.UserController;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.getQueryUsername;
import static viewutil.RequestUtil.removeSessionAttrLoggedOut;
import static viewutil.RequestUtil.removeSessionAttrLoginRedirect;

/**
 * Created by eigenaar on 26-1-2017.
 */
public class ContactController {

    public static Route contactPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.CONTACT);
    };
    public static Route handleContactPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserController controller = new UserController(getQueryUsername(request));


        return ViewUtil.render(request, model, Path.Template.CONTACT);
    };


}
