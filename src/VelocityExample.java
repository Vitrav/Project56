/**
 * Created by Dave on 25-10-16.
 */
import index.*;
import viewutil.*;

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

        get(Path.Web.INDEX,          IndexController.indexPage);
        get(Path.Web.SINGLEPAGE,     SingleProductController.singleProductPage);
        get(Path.Web.CART,           CartController.cartPage);
        get(Path.Web.SHOP,           ShopController.shopPage);
        after("*",                   Filters.addGzipHeader);


    }
}