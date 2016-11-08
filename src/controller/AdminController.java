package controller;

import com.mongodb.client.MongoCollection;
import model.Database;
import model.collection.CollectionManager;
import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import org.bson.Document;
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
import java.util.*;

import static viewutil.RequestUtil.*;
import java.util.*;

public class AdminController {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    private static final String salt = BCrypt.gensalt();

    public static Route adminPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        MongoCollection<Document> userCollection = Database.getInstance().getUserCollection();
        List<UserDocumentManager> users = new ArrayList<>();
        userCollection.find().iterator().forEachRemaining(user -> users.add(new UserDocumentManager(user)));

        model.put("userIsAdmin", true);
        model.put("allUserManagers", users);
        request.session().attribute("allUserManagers", users);
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };

    public static Route modifyPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
    };

    public static Route handleModifyPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        //Insert the session attributes in the model (so we keep modifyUser and modifyUserManager
        request.session().attributes().forEach(req -> {
            String att = req.toString();
            model.put(att, request.session().attribute(att));
        });
        model.put("userIsAdmin", true);

        String modifiedUser = (String) model.get("modifyUser");
        String username = getQueryUsername(request).length() >= 4 ? getQueryUsername(request) : modifiedUser;

        UserController controller = new UserController(modifiedUser);
        UserDocumentManager manager = (UserDocumentManager) model.get("modifyUserManager");

        //All current user values
        String oldCounty = manager.getAddressDocManager().getCountry();
        String oldPostalCode = manager.getAddressDocManager().getPostalCode();
        String oldCity = manager.getAddressDocManager().getCity();
        String oldStreet = manager.getAddressDocManager().getStreet();
        String oldNumber = manager.getAddressDocManager().getNumber();

        //All new user values
        String password = BCrypt.hashpw(getQueryPassword(request), salt);
        Date doB = getdoB(request).length() >= 6 ? DATE_FORMAT.parse(getdoB(request)) : null;
        String email = getEmail(request);
        String country = getCountry(request);
        String postalCode = getPostal(request);
        String city = getCity(request).toLowerCase();
        String street = getStreet(request).toLowerCase();
        String number = getNumber(request).toLowerCase();

        //Check inserted values and return the current screen with a message when it's invalid.
        if (getQueryUsername(request).length() >= 1) {
            if (controller.databaseHasUser(getQueryUsername(request))) {
                model.put("userExists", true);
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            } else if (!controller.usernameIsValid(getQueryUsername(request))) {
                model.put("userInvalid", true);
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            }
        } else if (getQueryPassword(request).length() >= 1) {
            if (!controller.passwordIsValid(getQueryPassword(request))) {
                model.put("passwordInvalid", true);
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            }
        } else if (doB != manager.getDateOfBirthAsDate() && doB != null) {
            if (!controller.validDob(getdoB(request))) {
                model.put("dobInvalid", true);
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            }
        } else if (email != manager.getEmail() && email.length() > 2) {
            if (!controller.validEmail(email)) {
                model.put("emailInvalid", true);
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            }
        } else if (postalCode != manager.getAddressDocManager().getPostalCode() && postalCode.length() >= 2) {
            if (!controller.validPostal(postalCode)) {
                model.put("postalInvalid", true);
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            }
        } else if (number.length() >= 1) {
            if (!controller.validStreetNumber(number)) {
                model.put("streetNumberInvalid", true);
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            }
        }
        if  (getQueryPassword(request).length() >= 4)
            manager.setPassword(password);
        if (getdoB(request).length() >= 6 && doB != null)
            manager.setDateOfBirth(doB);
        if (getEmail(request).length() > 1)
            manager.setEmail(email);

        //Update the address
        Address newAddress = new Address(country.length() > 1 ? country : oldCounty, city.length() > 1 ? city : oldCity, street.length() > 1 ? street : oldStreet, number.length() >= 1 ? number : oldNumber, postalCode.length() >= 6 ?  postalCode : oldPostalCode);
        manager.setAddress(new UserCollectionManager().createAddressDocument(newAddress));
        //Replace the user brcause it might be modified.
        request.session().attribute("modifyUser", username);
        model.put("modifyUser", username);
        if (username != modifiedUser) {
            //Insert the new user and drop the old one.
            new UserCollectionManager().insertUser(new User(username, salt, password, newAddress, manager.getAge(), manager.getDateOfBirthAsDate(), manager.getEmail(), manager.isAdmin(), manager.wishListIsPrivate(), false));
            Database.getInstance().getUserCollection().deleteOne(new UserCollectionManager().getUserDocument(modifiedUser));
            request.session().attribute("modifyUserManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(username)));
            model.put("modifyUserManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(username)));
        } else {
            request.session().attribute("modifyUserManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(username)));
            model.put("modifyUserManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(modifiedUser)));
        }
        model.put("valuesUpdated", true);
        return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
    };

    public static Route handleDeletePost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        request.session().attributes().forEach(req -> {
            String att = req.toString();
            model.put(att, request.session().attribute(att));});

        String modifyUser = (String) model.get("modifyUser");
        model.put("userIsAdmin", true);
        if (request.queryParams().iterator().hasNext()){
            //determine which button you press
            if (request.queryParams(request.queryParams().iterator().next()).equalsIgnoreCase("Yes")){
                //delete the selected user from the database
                Database.getInstance().getUserCollection().deleteOne(new UserCollectionManager().getUserDocument(modifyUser));
                return ViewUtil.render(request, model, Path.Template.INDEX);
            } else {
                //you don't delete a single thing, just return to the index page
                return ViewUtil.render(request, model, Path.Template.INDEX);
            }
        }
        return  ViewUtil.render(request, model, Path.Template.DELETESCREEN);
    };

    public static Route handleAdminPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("userIsAdmin", true);

        if (request.queryParams().iterator().hasNext()) {
            String user = request.queryParams().iterator().next();
            model.put("modifyUser", user);
            request.session().attribute("modifyUser", user);
            model.put("modifyUserManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(user)));

            String buttonName = request.queryParams(request.queryParams().iterator().next());
            if (buttonName.equalsIgnoreCase("Modify")) {
                request.session().attribute("modifyUserManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(user)));
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            } else if (buttonName.equalsIgnoreCase("Delete"))
                return ViewUtil.render(request, model, Path.Template.DELETESCREEN);
            else if (buttonName.equalsIgnoreCase("Block"))
                new UserDocumentManager(new UserCollectionManager().getUserDocument(user)).setBlocked(true);
            else if (buttonName.equalsIgnoreCase("Unblock"))
                new UserDocumentManager(new UserCollectionManager().getUserDocument(user)).setBlocked(false);
        }
        List<UserDocumentManager> users = new ArrayList<>();
        new UserCollectionManager().getCollection().find().iterator().forEachRemaining(user -> users.add(new UserDocumentManager(user)));
        model.put("allUserManagers", users);
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };


}
