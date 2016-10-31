package main; /**
 * Created by Dave on 25-10-16.
 */
import index.*;
import model.Database;
import model.collection.UserCollectionManager;
import user.*;
import viewutil.*;
import spark.Spark;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static spark.Spark.get;

/**
 * VelocityTemplateRoute example.
 */
public final class Application {

    public static UserDao UserDao;
//    public UserCollectionManager userCollect;
    public static void main(final String[] args) {
//        Spark.staticFileLocation("/sources");
        UserDao = new UserDao();
        Database database = Database.getInstance();
        database.getDatabase();
        UserCollectionManager userCollect = new UserCollectionManager();

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
        get(Path.Web.REGISTRATION, RegistrationController.registrationPage);
        after("*", Filters.addGzipHeader);
        post("/index", (request, response) -> {
            // Get foo then call your Java method
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            String age = request.queryParams("age");
            String email = request.queryParams("email");
            userCollect.insertUserRegister(username, password, age, email);
            System.out.println(username);
            return username;
        });



    }
}