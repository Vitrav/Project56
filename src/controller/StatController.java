package controller;

import controller.utils.ConUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import viewutil.Path;
import viewutil.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class StatController {

    public static Route statPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        ConUtil.searchGame(request,model);

        if (model.containsKey("games"))
            return ViewUtil.render(request, model, Path.Template.SHOP);
        ConUtil.addGames(model);
        return ViewUtil.render(request, model, Path.Template.STATISTICS);
    };

    public static String toJavascriptArray(String[] arr){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=0; i<arr.length; i++){
            sb.append("\"").append(arr[i]).append("\"");
            if(i+1 < arr.length){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
