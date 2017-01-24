package main;

import controller.*;
import controller.utils.ForgotPasswordController;
import model.Database;
import viewutil.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static spark.Spark.get;

public final class Application {

    public static void main(final String[] args) {
        port(4567);
        staticFiles.location("/sources");
        staticFiles.expireTime(600L);
        enableDebugScreen();
        before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);

        //all of the webpaths that are used on the website
        get(Path.Web.INDEX, controller.IndexController.indexPage);
        get(Path.Web.SINGLEPAGE, SingleProductController.singlePage);
        get(Path.Web.CART, CartController.cartPage);
        get(Path.Web.SHOP, ShopController.shopPage);
        post(Path.Web.SHOP, ShopController.shopPost);
        get(Path.Web.LOGIN, LoginController.loginPage);
        get(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        get(Path.Web.REGISTRATION, RegistrationController.registrationPage);
        get(Path.Web.MYACCOUNT, MyAccountController.accountPage);
        get(Path.Web.ADMINPANEL, AdminController.adminPage);
        get(Path.Web.MODIFYSCREEN, AdminController.modifyPage);
        get(Path.Web.WISHLIST, WishListController.wishListPage);
        get(Path.Web.FAVORITELIST, FavListController.favListPage);
        post(Path.Web.FAVORITELIST, FavListController.favListPage);
        get(Path.Web.STATISTICS, StatController.statPage);
        get(Path.Web.HISTORYLIST, HistoryListController.historyListPage);
        get(Path.Web.FORGOTPASSWORD, ForgotPasswordController.ForgotPassword);

        post(Path.Web.HISTORYLIST, HistoryListController.historyPost);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.MODIFYSCREEN, AdminController.handleModifyPost);
        post(Path.Web.ADMINPANEL, AdminController.handleAdminPost);
        post(Path.Web.REGISTRATION, RegistrationController.handleRegisterPost);
        post(Path.Web.DELETESCREEN, AdminController.handleDeletePost);
        post(Path.Web.WISHLIST, WishListController.handleWishListPost);
        post(Path.Web.CART, CartController.handleCartPost);
        post(Path.Web.CART, CartController.handleCartPost);

        //Add games to database.
//        new GameParser().addGamesToDB();

        Database.getInstance().getGameCollection().find().iterator().forEachRemaining(game ->
            get("/single-page/" + game.getInteger("id") + "/", SingleProductController.singlePage));

        after("*", Filters.addGzipHeader);
    }

}