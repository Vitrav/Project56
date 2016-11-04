package controller;

import com.mongodb.client.MongoCollection;
import model.Database;
import model.collection.UserCollectionManager;
import model.document.UserDocumentManager;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import user.UserController;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static viewutil.RequestUtil.*;

public class AdminController {

    public static Route adminPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        MongoCollection<Document> userCollection = Database.getInstance().getUserCollection();
        List<Document> users = new ArrayList<>();
        while (userCollection.find().iterator().hasNext())
            users.add(userCollection.find().iterator().next());
        model.put("allUsers", users);
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };

    public static Route handleSubmitPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        UserController controller = new UserController(getQueryUsername(request));
        if (!controller.databaseHasUser()) {
            model.put("userInvalid", true);
            return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
        }
        model.put("checkedUser", true);
        model.put("checkedUserDocument", new UserDocumentManager(new UserCollectionManager().getUserDocument(getQueryUsername(request))));
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };


}
