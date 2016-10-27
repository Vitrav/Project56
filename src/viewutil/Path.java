package viewutil;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter public static final String INDEX = "/index/";
        @Getter public static final String LOGIN = "/single-product/";
        @Getter public static final String LOGOUT = "/logout/";
//        @Getter public static final String BOOKS = "/books/";
//        @Getter public static final String ONE_BOOK = "/books/:isbn/";
    }

    public static class Template {
        public final static String INDEX = "sources/HTML/index.vm";
        public final static String LOGIN = "sources/HTML/single-product.vm";
        public final static String BOOKS_ALL = "/velocity/book/all.vm";
//        public static final String BOOKS_ONE = "/velocity/book/one.vm";
        public static final String NOT_FOUND = "/velocity/notFound.html";
    }

}
