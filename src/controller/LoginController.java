package controller;


import controller.utils.ConUtil;
import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import spark.Route;
import user.UserController;
import viewutil.Path;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;;
import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.*;

public class LoginController {

    public static Route loginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserController controller = new UserController(getQueryUsername(request));
        //user authentication
        if (!controller.authenticate(getQueryPassword(request))) {
            model.put("authenticationFailed", true);
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }
        //check if user is blocked or not
        if (new UserDocumentManager(new UserCollectionManager().getUserDocument(getQueryUsername(request))).getIsBlocked()) {
            model.put("blocked", true);
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }
        //check if user is admin or not
        if (controller.adminStatus())
            model.put("userIsAdmin", true);

        //request a session for the user so he/she is in fact logged in
        request.session().attribute("currentUser", getQueryUsername(request));
        ConUtil.addModelVariables(request, model);
        return ViewUtil.render(request, model, Path.Template.INDEX);
    };

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);
        return null;
    };

    // The origin of the request (request.pathInfo()) is saved in the session so
    // the User can be redirected back after login
    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            response.redirect(Path.Web.LOGIN);
        }
    };

}
