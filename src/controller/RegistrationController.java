package controller;

import model.collection.UserCollectionManager;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Route;
import user.Address;
import user.User;
import user.UserController;
import viewutil.Path;
import viewutil.ViewUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.*;

public class RegistrationController {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private static final String salt = BCrypt.gensalt();
    private static final UserCollectionManager userCollectionManager = new UserCollectionManager();

    public static Route registrationPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));

        return ViewUtil.render(request, model, Path.Template.REGISTRATION);
    };

    public static Route handleRegisterPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserController controller = new UserController(getQueryUsername(request));

        //get the information from the fields on the website and store it in strings
        String username = getQueryUsername(request);
        String password = getQueryPassword(request);
        String doB = getdoB(request);
        String email = getEmail(request);
        String country = getCountry(request).toLowerCase();
        String postalCode = getPostal(request);
        String city = getCity(request).toLowerCase();
        String street = getStreet(request).toLowerCase();
        String number = getNumber(request).toLowerCase();

        //validation checks
        if (controller.databaseHasUser()) {
            model.put("userExists", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (!controller.usernameIsValid()) {
            model.put("userInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (!controller.passwordIsValid(password)) {
            model.put(" passwordInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (!controller.validDob(doB)) {
            model.put("dobInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (!controller.validEmail(email)) {
            model.put("emailInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (country.length() == 0) {
            model.put("countryInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (!controller.validPostal(postalCode)) {
            model.put("postalInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (city.length() == 0)  {
            model.put("cityInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (street.length() == 0) {
            model.put("streetInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        } else if (!controller.validStreetNumber(number)) {
            model.put("streetNumberInvalid", true);
            return ViewUtil.render(request, model, Path.Template.REGISTRATION);
        }

        Date dateOfBirth = dateFormat.parse(doB);
        int age = calculateAge(dateOfBirth);
        //insert the validated user into the database
        userCollectionManager.insertUser(new User(
                username,
                salt,
                BCrypt.hashpw(password, salt),
                new Address(country, city, street, number, postalCode),
                age,
                dateOfBirth,
                email,
                false, //admin status
                false, //wishlist.vm status
                false));//blocked status is false
        System.out.println("User created.");
        return ViewUtil.render(request, model, Path.Template.INDEX);
    };

    public static int calculateAge(Date dateOfBirth) {
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