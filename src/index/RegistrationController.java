package index;

import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.*;

/**
 * Created by Dave on 31-10-16.
 */
public class RegistrationController {

    public static Route registrationPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));

        return ViewUtil.render(request, model, Path.Template.REGISTRATION);
    };

    public static Route handleLoginPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String username = getQueryUsername(request);
        String password = getQueryPassword(request);



        return ViewUtil.render(request, model, Path.Template.REGISTRATION);
    };



//    public static Route handleLoginPost = (Request request, Response response) -> {
//        Map<String, Object> model = new HashMap<>();
//        UserController controller = new UserController(getQueryUsername(request));
//        if (!controller.authenticate(getQueryPassword(request))) {
//            model.put("authenticationFailed", true);
//            return ViewUtil.render(request, model, Path.Template.LOGIN);
//        }
//        model.put("authenticationSucceeded", true);
//        request.session().attribute("currentUser", getQueryUsername(request));
//        if (getQueryLoginRedirect(request) != null) {
//            response.redirect(getQueryLoginRedirect(request));
//        }
//        return ViewUtil.render(request, model, Path.Template.LOGIN);
//    };
}