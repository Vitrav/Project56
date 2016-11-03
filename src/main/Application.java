package main; /**
 * Created by Dave on 25-10-16.
 */
import controller.*;
import model.collection.GameCollectionManager;
import viewutil.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static spark.Spark.get;

public final class Application {

    public static void main(final String[] args) {
//        Spark.staticFileLocation("/sources");
        GameCollectionManager test = new GameCollectionManager();
        port(4567);
        staticFiles.location("/sources");
        staticFiles.expireTime(600L);
        enableDebugScreen();
        Map<String, Object> attributes = new HashMap<>();
        before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);
        attributes.put("GAMES", test.getGameDocument(1));
        get(Path.Web.INDEX, controller.IndexController.indexPage);
        get(Path.Web.SINGLEPAGE, SingleProductController.singleProductPage);
        get(Path.Web.CART, CartController.cartPage);
        get(Path.Web.SHOP, ShopController.shopPage);
        get(Path.Web.LOGIN, LoginController.loginPage);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        get(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        get(Path.Web.REGISTRATION, RegistrationController.registrationPage);
        get(Path.Web.MYACCOUNT, MyAccountController.accountPage);
        get(Path.Web.ADMINPANEL, AdminController.adminPage);
        post(Path.Web.ADMINPANEL, AdminController.handleSubmitPost);
        post(Path.Web.REGISTRATION, RegistrationController.handleRegisterPost);
        after("*", Filters.addGzipHeader);
        System.out.println(test.getGameDocument(1));

    }

}