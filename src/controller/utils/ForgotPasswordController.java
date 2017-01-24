package controller.utils;

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

import static viewutil.RequestUtil.*;
import static viewutil.RequestUtil.getQueryUsername;

/**
 * Created by eigenaar on 23-1-2017.
 */
public class ForgotPasswordController {

    public static Route ForgotPassword = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.FORGOTPASSWORD);
    };

    public static Route handleForgotPasswordPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserController controller = new UserController(getEmail(request));
        //get the email adress from the field on the website and store it in strings


        //email validation
        if (!controller.authenticateEmail(getEmail(request))) {
            model.put("emailAuthenticationFailed", true);
            return ViewUtil.render(request, model, Path.Template.FORGOTPASSWORD);
        }

        return ViewUtil.render(request, model, Path.Template.FORGOTPASSWORD);
    };

}
