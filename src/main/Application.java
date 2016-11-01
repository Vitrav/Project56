package main; /**
 * Created by Dave on 25-10-16.
 */
import index.*;
import model.Database;
import model.collection.CollectionManager;
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
        String salt = BCrypt.gensalt();
//        Spark.staticFileLocation("/sources");
        UserCollectionManager userCollectionManager = new UserCollectionManager();

        port(4567);
        staticFiles.location("/sources");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        before("*",                  Filters.addTrailingSlashes);
        before("*",                  Filters.handleLocaleChange);

        get(Path.Web.INDEX,         index.IndexController.indexPage);
        get(Path.Web.SINGLEPAGE,    SingleProductController.singleProductPage);
        get(Path.Web.CART,          CartController.cartPage);
        get(Path.Web.SHOP,          ShopController.shopPage);
        get(Path.Web.LOGIN,         LoginController.loginPage);
        post(Path.Web.LOGIN,        LoginController.handleLoginPost);
        post(Path.Web.LOGOUT,       LoginController.handleLogoutPost);
        get(Path.Web.REGISTRATION,  RegistrationController.registrationPage);
        after("*", Filters.addGzipHeader);
        post("/index", (request, response) -> {
            // Get foo then call your Java method
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            String doB= request.queryParams("doB");
            String email = request.queryParams("email");
            String country = request.queryParams("country");
            String postalCode = request.queryParams("postalcode");
            String city = request.queryParams("city");
            String street = request.queryParams("street");
            String number = request.queryParams("number");

            // Create a format in which a string containing the date will be parsed
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date dateOfBirth= dateFormat.parse(doB);

            // calculate age
            int age = calculateAge(dateOfBirth);

            // insert a user in the database
            userCollectionManager.insertUser(new User(username, salt, BCrypt.hashpw(password, salt), new Address(country, city, street, number, postalCode), age, dateOfBirth, email, false, false));
            System.out.println(dateOfBirth);
            return username;
        });



    }

    private static int calculateAge(Date dateOfBirth){
        //get the birth date
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateOfBirth);

        //get the current date
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        //return the current date - the birth date for the age
        return currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
    }
}