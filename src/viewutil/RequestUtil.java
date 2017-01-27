package viewutil;

import model.document.GameDocumentManager;
import spark.Request;
import model.document.GameDocumentManager.*;

//Used to get specific queryParams from a request (see RegistrationController).
public class RequestUtil {

    public static String getQueryLocale(Request request) {
        return request.queryParams("locale");
    }

    public static String getParamIsbn(Request request) {
        return request.params("isbn");
    }

    public static String getQueryUsername(Request request) {
        return request.queryParams("username");
    }
    public static String getQueryEmail(Request request) {
        return request.queryParams("email");
    }

    public static String getQueryUserEmail(Request request) {
        return request.queryParams("email");
    }

    public static String getQueryOldPassword(Request request) {
        return request.queryParams("oldpassword");
    }
    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }

    public static String getdoB(Request request) {
        String doB = request.queryParams("doB");
        if (doB.contains("-"))
            doB = doB.replaceAll("-", "/");
        return doB;
    }

    public static String getEmail(Request request) {
        return request.queryParams("email");
    }

    public static String getCountry(Request request) {
        return request.queryParams("country");
    }

    public static String getPostal(Request request) {
        return request.queryParams("postalcode");
    }

    public static String getCity(Request request) {
        return request.queryParams("city");
    }

    public static String getStreet(Request request) {
        return request.queryParams("street");
    }

    public static String getNumber(Request request) {
        return request.queryParams("number");
    }

    public static String getQueryLoginRedirect(Request request) {
        return request.queryParams("loginRedirect");
    }

    public static String getSessionLocale(Request request) {
        return request.session().attribute("locale");
    }

    public static String getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("application/json");
    }
}