package viewutil;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter public static final String INDEX = "/index/";
        @Getter public static final String SINGLEPAGE = "/single-page/";
        @Getter public static final String CART = "/cart/";
        @Getter public static final String SHOP = "/shop/";
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String REGISTRATION = "/registration/";
        @Getter public static final String MYACCOUNT = "/myaccount/";
        @Getter public static final String ADMINPANEL = "/adminpanel/";
        @Getter public static final String MODIFYSCREEN = "/modify/";
        @Getter public static final String DELETESCREEN = "/delete/";
        @Getter public static final String WISHLIST = "/wishlist/";
    }

    public static class Template {
        public final static String INDEX = "/sources/HTML/index/index.vm";
        public final static String SINGLEPAGE = "/sources/HTML/singlePage/single-product.vm";
        public final static String CART = "/sources/HTML/cart/cart.vm";
        public static final String SHOP = "/sources/HTML/shop/shop.vm";
        public static final String LOGIN = "/sources/HTML/login/login.vm";
        public static final String NOT_FOUND = "/velocity/notFound.html";
        public static final String REGISTRATION = "/sources/HTML/registration/registration.vm";
        public static final String MYACCOUNT = "/sources/HTML/account/account.vm";
        public static final String ADMINPANEL = "/sources/HTML/admin/admin.vm";
        public static final String MODIFYSCREEN = "/sources/HTML/admin/modify.vm";
        public static final String DELETESCREEN = "/sources/HTML/admin/delete.vm";
        public static final String WISHLIST = "/sources/HTML/account/wishlist.vm";
    }

}
