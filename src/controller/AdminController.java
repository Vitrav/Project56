package controller;

import spark.Request;
import spark.Response;
import spark.Route;
import user.UserController;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.*;

public class AdminController {

    public static Route adminPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };

    public static Route handleSubmitPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        UserController controller = new UserController(getQueryUsername(request));
        if (!controller.databaseHasUser()) {
            model.put("userInvalid", true);
            return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
        }
        request.session().attribute("currentUser", getQueryUsername(request));
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };


}
