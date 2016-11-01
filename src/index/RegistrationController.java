package index;

import model.collection.UserCollectionManager;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Route;
import user.Address;
import user.User;
import viewutil.Path;
import viewutil.RequestUtil;
import viewutil.ViewUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.*;

/**
 * Created by Dave on 31-10-16.
 */
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
        String username = getQueryUsername(request);
        String password = getQueryPassword(request);
        String doB = getdoB(request);
        String email = getEmail(request);
        String country = getCountry(request);
        String postalCode = getPostal(request);
        String city = getCity(request);
        String street = getStreet(request);
        String number = getNumber(request);
        Date dateOfBirth = dateFormat.parse(doB);
        int age = calculateAge(dateOfBirth);

        userCollectionManager.insertUser(new User(username, salt, BCrypt.hashpw(password, salt), new Address(country, city, street, number, postalCode), age, dateOfBirth, email, false, false));
        System.out.println("User created.");
        return ViewUtil.render(request, model, Path.Template.REGISTRATION);
    };

    private static int calculateAge(Date dateOfBirth) {
        //get the birth date
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateOfBirth);

        //get the current date
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        //return the current date - the birth date for the age
        return currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
    }

//    String doB = RequestUtil.getdoB(request);
//    String email = request.queryParams("email");
//    String country = request.queryParams("country");
//    String postalCode = request.queryParams("postalcode");
//    String city = request.queryParams("city");
//    String street = request.queryParams("street");
//    String number = request.queryParams("number");



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