package controller;

import viewutil.Path;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.clientAcceptsHtml;

//import main.viewutil.Path;

/**
 * Created by Dave on 26-10-16.
 */
public class SingleProductController {

    public static Route fetchOneBook = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);

            HashMap<String, Object> model = new HashMap<>();
            System.out.println((request.url()));

            model.put("book", model);
//            return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);

//        if (clientAcceptsJson(request)) {
//            return dataToJson(bookDao.getBookByIsbn(getParamIsbn(request)));
//        }
        return ViewUtil.notAcceptable.handle(request, response);
    };
}
