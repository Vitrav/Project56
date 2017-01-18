package controller;


import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;
import org.bson.Document;
import parser.Game;
import viewutil.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static viewutil.RequestUtil.getQueryPassword;
import static viewutil.RequestUtil.getSessionCurrentUser;


public class CartController {

    public static Route cartPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserCollectionManager userCollectionManager = new UserCollectionManager();
        UserDocumentManager userDocumentManager = new UserDocumentManager(userCollectionManager.getUserDocument(getSessionCurrentUser(request)));
        GameCollectionManager gameCollection = new GameCollectionManager();
        GameDocumentManager gameDocumentManager;
        List<GameDocumentManager> cartGameList = new ArrayList<>();

        if (userDocumentManager.isAdmin()){
            model.put("userIsAdmin", true);
        }

        List<Integer> games = new ArrayList<Integer>();
        List<Document> cartGames = new ArrayList<Document>();
        userDocumentManager.getCartItems().iterator().forEachRemaining(game -> games.add(game.getInteger("id")));

        for (int game : games) {
            Document query = new Document();
            query.put("id", game);
            cartGames.add(gameCollection.getCollection().find(query).iterator().tryNext());
        }

        for (Document game : cartGames){
            gameDocumentManager = new GameDocumentManager(game);
            System.out.println(gameDocumentManager.getName());
            cartGameList.add(gameDocumentManager);
        }
//        cartGames.iterator().forEachRemaining(game -> cartGameList.add(new GameDocumentManager(game)));
        System.out.println(cartGameList);

        model.put("cartGamesInfo", cartGameList);
        // The sources.HTML files are located under the resources directory
        return ViewUtil.render(request, model, viewutil.Path.Template.CART);
    };
}
