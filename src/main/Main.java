package main;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MongoDatabase;


/**
 * Created by Dave on 24-10-16.
 */


//Main class which extends from Application Class
public class Main extends Application {
	
    //This is our PrimaryStage (It contains everything)
    private Stage primaryStage;

    //This is the BorderPane of RootLayout
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        //1) Declare a primary stage (Everything will be on this stage)
        this.primaryStage = primaryStage;
        
        //Optional: Set a title for primary stage
        this.primaryStage.setTitle("Webshop");

        //2) Initialize RootLayout
        initRootLayout();

    }

    //Initializes the root layout.
    public void initRootLayout() {
        try {
            //First, load root layout from RootLayout.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //Second, show the scene containing the root layout.
            Scene scene = new Scene(rootLayout); //We are sending rootLayout to the Scene.
            primaryStage.setScene(scene); //Set the scene in primary stage.

            //Third, show the primary stage
            primaryStage.show(); //Display the primary stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
//      launch(args);
    	
    	//Get the database using singleton.
    	MongoDatabase mongo = MongoDatabase.getInstance(); 
    	//Insert two games in the game collection.

    	mongo.insertNewGameDocument(mongo.createGameDocument(1, "Game", "Awesome game", 12.22F, 18, "PC", "Shooter", new Date()));
    	mongo.insertNewGameDocument(mongo.createGameDocument(2, "Great Game", "Great game, play this 24/7", 20.00F, 7, "PS3", "Shooter", new Date()));
    	
    	//Insert a new user in the user collection.
    	mongo.insertUser("SuperUser12", "pass123", 18, "SuperUser12@gmail.com", false, mongo.createAddressDocument("Netherlands", "Rotterdam", "Wijnhaven", 107, "1264 ZF"), Arrays.asList(1), Arrays.asList(), Arrays.asList(), Arrays.asList());
    	
    	//Add a history document to the user
    	mongo.addHistoryDocument("SuperUser12", mongo.createHistoryDocument(1, new Date()));
    	mongo.addHistoryDocument("SuperUser12", mongo.createHistoryDocument(2, new Date()));
    	mongo.addToFavouriteList("SuperUser12", 2);
    	mongo.addToWishList("SuperUser12", 1);
    	
//    	mongo.dropCollection(mongo.getGameCollection());
//    	mongo.dropCollection(mongo.getUserCollection());
    }
}