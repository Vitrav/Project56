/**
 * Created by Dave on 25-10-16.
 */
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;

/**
 * VelocityTemplateRoute example.
 */
public final class VelocityExample {
    public static void main(final String[] args) {
        Spark.staticFileLocation("/sources");
        get("/hello", (request, response) -> {
//            cssmodel.put("owl", "/sources.css/owl.carousel.sources.css");
            Map<String, Object> model = new HashMap<>();
            model.put("hello", "Velocity World");
            model.put("person", new Person("Foobar"));

            // The vm files are located under the resources directory
            return new ModelAndView(model, "index.html");

        }, new VelocityTemplateEngine());

    }

    public static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}