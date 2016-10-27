package viewutil;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter public static final String INDEX = "/index/";
        @Getter public static final String SINGLEPAGE = "/single-product/";
        @Getter public static final String CART = "/cart/";
        @Getter public static final String SHOP = "/shop/";
//        @Getter public static final String ONE_BOOK = "/books/:isbn/";
    }

    public static class Template {
        public final static String INDEX = "/sources/HTML/index/index.vm";
        public final static String SINGLEPAGE = "/sources/HTML/singlePage/single-product.vm";
        public final static String CART = "/sources/HTML/cart/cart.vm";
        public static final String SHOP = "/sources/HTML/shop/shop.vm";
        public static final String NOT_FOUND = "/velocity/notFound.html";
    }

}
