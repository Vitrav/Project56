package controller;

import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import spark.Route;
import user.UserController;
import viewutil.Path;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;;
import java.util.ArrayList;
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

        UserDocumentManager manager = new UserDocumentManager(getQueryUsername(request));
        ShopController.getUserItems().get(request.session().id()).forEach(item -> manager.addCartItem(item));
        ShopController.getUserItems().put(request.session().id(), new ArrayList<>());
        //request a session for the user so he/she is in fact logged in
        request.session().attribute("currentUser", getQueryUsername(request));
        return ViewUtil.render(request, model, Path.Template.INDEX);
    };

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);
        return null;
    };

}
