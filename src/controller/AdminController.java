package controller;

import com.mongodb.client.MongoCollection;
import model.Database;
import model.collection.CollectionManager;
import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.*;

public class AdminController {
    public static Route adminPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        MongoCollection<Document> userCollection = Database.getInstance().getUserCollection();
        List<UserDocumentManager> users = new ArrayList<>();
        userCollection.find().iterator().forEachRemaining(user -> users.add(new UserDocumentManager(user)));

        model.put("userIsAdmin", true);
        model.put("allUserManagers", users);
        model.put("modifyUser", "1");
        model.put("viewUtil", new ViewUtil());
        request.session().attribute("viewUtil", new ViewUtil());
        request.session().attribute("modifyUser", "1");
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };

    public static Route modifyPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
    };

    public static Route handleDeletePost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("userIsAdmin", true);
        if (request.queryParams().iterator().hasNext()){
            String user = request.queryParams().iterator().next();
            if (request.queryParams(request.queryParams().iterator().next()).equalsIgnoreCase("Yes")){
                System.out.println("Yes");
                Database.getInstance().getUserCollection().findOneAndDelete(new UserCollectionManager().getUserDocument(user));
                return ViewUtil.render(request, model, Path.Template.INDEX);
            } else {
                System.out.println("No");
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
            if (request.queryParams(request.queryParams().iterator().next()).equalsIgnoreCase("Modify")) {
                System.out.println("Modify button");
                model.put("modifyUserManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(user)));
                return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
            } else {
                System.out.println("Delete button");
                model.put("modifyUserManager", new UserDocumentManager(new UserCollectionManager().getUserDocument(user)));
                return ViewUtil.render(request, model, Path.Template.DELETESCREEN);
            }
        }
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };


}
