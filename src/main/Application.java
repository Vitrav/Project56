package main; /**
 * Created by Dave on 25-10-16.
 */
import index.*;
import model.Database;
import model.collection.UserCollectionManager;
import org.mindrot.jbcrypt.BCrypt;
import user.*;
import viewutil.*;
import spark.Spark;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static spark.Spark.get;

/**
 * VelocityTemplateRoute example.
 */
public final class Application {

    public static void main(final String[] args) {
//        Spark.staticFileLocation("/sources");

        port(4567);
        staticFiles.location("/sources");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);

        get(Path.Web.INDEX, index.IndexController.indexPage);
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