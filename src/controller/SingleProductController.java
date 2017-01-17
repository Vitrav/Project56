package controller;

import model.collection.GameCollectionManager;
import model.document.GameDocumentManager;
import viewutil.Path;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class SingleProductController {

    public static Route singlePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        String id = request.url().substring(request.url().indexOf("single-page/"), request.url().length() - 1).replace("single-page/", "");
        model.put("currentGame", new GameDocumentManager(new GameCollectionManager().getGameDocument(Integer.parseInt(id))));
        return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
    };

}
