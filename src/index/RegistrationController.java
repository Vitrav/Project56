package index;

import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.removeSessionAttrLoggedOut;
import static viewutil.RequestUtil.removeSessionAttrLoginRedirect;

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
}