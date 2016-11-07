package controller;

import com.mongodb.client.MongoCollection;
import model.Database;
import model.document.UserDocumentManager;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {

    public static Route adminPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        MongoCollection<Document> userCollection = Database.getInstance().getUserCollection();
        List<UserDocumentManager> users = new ArrayList<>();
        userCollection.find().iterator().forEachRemaining(user -> users.add(new UserDocumentManager(user)));
        model.put("allUserManagers", users);
        model.put("modifyUser", "1");
        request.session().attribute("modifyUser", "1");
        return ViewUtil.render(request, model, Path.Template.ADMINPANEL);
    };

    public static Route modifyPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
    };

    public static Route handleAdminPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        request.session().attributes().forEach(System.out::println);
        String modifyUser = request.session().attribute("modifyUser");
        System.out.println(modifyUser);
        model.put("modifyUser", modifyUser);
        return ViewUtil.render(request, model, Path.Template.MODIFYSCREEN);
    };

}
