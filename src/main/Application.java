package main; /**
 * Created by Dave on 25-10-16.
 */
import controller.*;
import viewutil.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static spark.Spark.get;

public final class Application {

    public static void main(final String[] args) {
//        Spark.staticFileLocation("/sources");

        port(4567);
        staticFiles.location("/sources");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);

        get(Path.Web.INDEX, controller.IndexController.indexPage);
        get(Path.Web.SINGLEPAGE, SingleProductController.singleProductPage);
        get(Path.Web.CART, CartController.cartPage);
        get(Path.Web.SHOP, ShopController.shopPage);
        get(Path.Web.LOGIN, LoginController.loginPage);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        get(Path.Web.REGISTRATION, RegistrationController.registrationPage);
        post(Path.Web.REGISTRATION, RegistrationController.handleRegisterPost);
        after("*", Filters.addGzipHeader);
    }

}