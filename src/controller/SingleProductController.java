package controller;

import viewutil.Path;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

//import main.viewutil.Path;

/**
 * Created by Dave on 26-10-16.
 */
public class SingleProductController {

    public static Route singleProductPage=(Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        // The sources.HTML files are located under the resources directory
//        return new ModelAndView(main.model, main.viewutil.Path.Template.INDEX);
        return ViewUtil.render(request, model, Path.Template.SINGLEPAGE);
    };

}