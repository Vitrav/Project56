package viewutil;

import controller.ShopController;
import controller.utils.ConUtil;
import model.collection.UserCollectionManager;
import model.document.CartItemDocumentManager;
import model.document.CartListDocManager;
import model.document.UserDocumentManager;
import org.apache.commons.collections.map.HashedMap;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static viewutil.RequestUtil.*;
import static viewutil.RequestUtil.getSessionLocale;

public class ViewUtil {

    // Renders a template given a main.model and a request
    // The request is needed to check the User session for language settings
    // and to see if the User is logged in
    public static String render(Request request, Map<String, Object> model, String templatePath) {
        model.put("currentUser", getSessionCurrentUser(request));
        ConUtil.addAllGames(model);
        model.put("WebPath", Path.Web.class); // Access application URLs from templates
        if (!ShopController.getUserItems().containsKey(request.session().id()))
            ShopController.getUserItems().put(request.session().id(), new ArrayList<>());

        if (getSessionCurrentUser(request) != null) {
            model.put("authenticationSucceeded", true);
            UserDocumentManager userDocumentManager = new UserDocumentManager(new UserCollectionManager().getUserDocument(getSessionCurrentUser(request)));
            if (userDocumentManager.isAdmin())
                model.put("userIsAdmin", true);
            model.put("userDocumentManager", userDocumentManager);
            model.put("hasManager", true);
        } else
            model.put("cartManager", new CartListDocManager(ShopController.getUserItems().get(request.session().id())));
        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }

    public static Route notFound = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    };

    private static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }
}
