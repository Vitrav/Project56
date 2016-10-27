/**
 * Created by Dave on 25-10-16.
 */
import index.*;
import viewutil.*;
import spark.Spark;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static spark.Spark.get;

/**
 * VelocityTemplateRoute example.
 */
public final class VelocityExample {
    public static void main(final String[] args) {
//        Spark.staticFileLocation("/sources");


        port(4567);
        staticFiles.location("/sources");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        before("*",                  Filters.addTrailingSlashes);
        before("*",                  Filters.handleLocaleChange);

        get(Path.Web.INDEX,          index.indexController.indexPage);
        get(Path.Web.SINGLEPAGE,     singleProductController.singleProductPage);
        get(Path.Web.CART,           cartController.cartPage);
        after("*",                   Filters.addGzipHeader);


    }
}