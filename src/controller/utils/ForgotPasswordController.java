package controller.utils;

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
 * Created by eigenaar on 23-1-2017.
 */
public class ForgotPasswordController {

    public static Route ForgotPassword = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.FORGOTPASSWORD);
    };

}
