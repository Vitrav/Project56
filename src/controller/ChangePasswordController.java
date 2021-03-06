package controller;

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

public class ChangePasswordController {

    public static Route changePasswordPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.CHANGEPASSWORD);
    };

    public static Route handleChangePasswordPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserController controller = new UserController(getQueryUsername(request));

        UserDocumentManager manager = (UserDocumentManager) model.get("modifyUserManager");
        String oldPass = manager.getPassword();
        //user authentication

        String newPassword = getQueryPassword(request);

      /*  if (!controller.authenticate(getQueryPassword(request))) {
            model.put("authenticationFailed", true);
            return ViewUtil.render(request, model, Path.Template.CHANGEPASSWORD);
        }
        /*if (!controller.passwordIsValid(newpassword)) {
            model.put("passwordInvalid", true);
            return ViewUtil.render(request, model, Path.Template.CHANGEPASSWORD);
        }*/
        controller.chancePassword(oldPass,newPassword);

        return ViewUtil.render(request, model, Path.Template.CHANGEPASSWORD);
    };



}
