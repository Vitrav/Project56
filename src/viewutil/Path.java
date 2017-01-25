package viewutil;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter public static final String INDEX = "/index/";
        @Getter public static final String SINGLEPAGE = "/single-page/";
        @Getter public static final String CART = "/cart/";
        @Getter public static final String SHOP = "/shop/1/";
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String REGISTRATION = "/registration/";
        @Getter public static final String MYACCOUNT = "/myaccount/";
        @Getter public static final String ADMINPANEL = "/adminpanel/";
        @Getter public static final String MODIFYSCREEN = "/modify/";
        @Getter public static final String DELETESCREEN = "/delete/";
        @Getter public static final String WISHLIST = "/wishList/";
        @Getter public static final String FAVORITELIST = "/favList/";
        @Getter public static final String STATISTICS = "/stat/";
        @Getter public static final String HISTORYLIST = "/historyList/";
        @Getter public static final String PURCHASESUCCESSFUL = "/purchaseSuccessful/";
        @Getter public static final String FORGOTPASSWORD = "/forgotpassword/";
        @Getter public static final String CHANGEPASSWORD = "/changepassword/";
        @Getter public static final String GAMESTOCK = "/gs/";
        @Getter public static final String AGEINFO = "/cai/";
        @Getter public static final String PURCHASES = "/ppd/";
    }

    public static class Template {
        public final static String INDEX = "/sources/HTML/index/index.vm";
        public final static String SINGLEPAGE = "/sources/HTML/singlePage/single-product.vm";
        public final static String CART = "/sources/HTML/cart/cart.vm";
        public static final String SHOP = "/sources/HTML/shop/shop.vm";
        public static final String LOGIN = "/sources/HTML/account/login.vm";
        public static final String NOT_FOUND = "/velocity/notFound.html";
        public static final String REGISTRATION = "/sources/HTML/account/registration.vm";
        public static final String MYACCOUNT = "/sources/HTML/account/account.vm";
        public static final String HISTORYLIST = "/sources/HTML/account/historyList.vm";
        public static final String ADMINPANEL = "/sources/HTML/admin/admin.vm";
        public static final String MODIFYSCREEN = "/sources/HTML/admin/modify.vm";
        public static final String DELETESCREEN = "/sources/HTML/admin/delete.vm";
        public static final String STATISTICS = "/sources/HTML/admin/statistics.vm";
        public static final String FAVORITELIST = "/sources/HTML/account/favList.vm";
        public static final String WISHLIST = "/sources/HTML/account/wishList.vm";
        public static final String PURCHASESUCCESSFUL = "/sources/HTML/cart/purchase.vm";
        public static final String FORGOTPASSWORD = "/sources/HTML/account/forgotpassword.vm";
        public static final String CHANGEPASSWORD = "/sources/HTML/account/changepassword.vm";
        public static final String GAMESTOCK = "/sources/HTML/admin/graphs/gamestock.vm";
        public static final String AGEINFO = "/sources/HTML/admin/graphs/ageinfo.vm";
        public static final String PURCHASES = "/sources/HTML/admin/graphs/purchases.vm";
    }

}
