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

import static viewutil.RequestUtil.*;

public class ForgotPasswordController {

    public static Route ForgotPassword = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.FORGOTPASSWORD);
    };

    public static Route handleForgotPasswordPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserController controller = new UserController(getQueryEmail(request));
        //get the email adress from the field on the website and store it in strings

        String email = getEmail(request);

        //email validation
        if (!controller.databaseHasEmail(email)) {
            model.put("emailDoesntExcists", true);
            return ViewUtil.render(request, model, Path.Template.FORGOTPASSWORD);
        }
        return ViewUtil.render(request, model, Path.Template.FORGOTPASSWORD);
    };
}



