package main;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;
import model.document.AddressDocumentManager;
import model.document.GameDocumentManager;
import model.document.UserDocumentManager;


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
    	//launch(args);
    	
    	GameCollectionManager gameCollectionManager = new GameCollectionManager();
    	UserCollectionManager userCollectionManager = new UserCollectionManager();
    	
    	//Insert two games in the game collection.
    	gameCollectionManager.insertNewGameDocument(gameCollectionManager.createGameDocument(1, "Game", "Awesome game", "EA", 12.22, 18, "PC", "Shooter", 10, new Date()));
    	gameCollectionManager.insertNewGameDocument(gameCollectionManager.createGameDocument(2, "Great Game", "Great game, play this 24/7", "Nintendo", 20.00, 7, "PS3", "Shooter", 2, new Date()));
    	
    	//Insert a new user in the user collection.
    	userCollectionManager.insertUser("SuperUser12", "pass123", 18, new Date(), "SuperUser12@gmail.com", false, userCollectionManager.createAddressDocument("Netherlands", "Rotterdam", "Wijnhaven", "107", "1264 ZF"), Arrays.asList(1), Arrays.asList(), Arrays.asList(), Arrays.asList());
    	
    	
    	//Add a history documents to the user
    	if (!userCollectionManager.addHistoryDocument("SuperUser12", userCollectionManager.createHistoryDocument(1, new Date())))
    		System.out.println("user or game can not be found");
     	if (!userCollectionManager.addHistoryDocument("SuperUser12", userCollectionManager.createHistoryDocument(2, new Date())))
    		System.out.println("user or game can not be found");
     	//Add fav game to the user
    	if (!userCollectionManager.addToFavouriteList("SuperUser12", 2))
    		System.out.println("User or game can not be found.");
    	//Add game to wishlist
    	if (!userCollectionManager.addToWishList("SuperUser12", 1))
    		System.out.println("User or game can not be found.");
    	
    	//Print and change userinfo
    	System.out.println("userinfo:");
    	UserDocumentManager userDocManager = new UserDocumentManager(userCollectionManager.getUserDocument("SuperUser12"));
    	userDocManager.setName("NewName12");
    	System.out.println(userDocManager.getName());
    	userDocManager.setAge(20);
    	System.out.println(userDocManager.getAge());
    	userDocManager.setDateOfBirth(new Date());
    	System.out.println(userDocManager.getDateOfBirth());
    	userDocManager.setEmail("test@gmail.com");
    	System.out.println(userDocManager.getEmail());
    	userDocManager.setPassword("newpass123");
    	System.out.println(userDocManager.getPassword());
    	userDocManager.setAdmin();
    	System.out.println(userDocManager.isAdmin());
    	System.out.println("fav list");
    	userDocManager.addFavouriteItem(4);
    	userDocManager.getFavouriteList().forEach(System.out::println);
    	System.out.println("wish list");
    	userDocManager.addWishItem(4);
    	userDocManager.getWishList().forEach(System.out::println);
    	System.out.println("cart items");
    	userDocManager.addCartItem(4);
    	userDocManager.getCartItems().forEach(System.out::println);
    	
    	//Print and change address info
    	System.out.println("\naddress info");
    	AddressDocumentManager addressDocManager = new AddressDocumentManager(userDocManager.getAddress());
    	addressDocManager.setCity("newCity");
    	System.out.println(addressDocManager.getCity());
    	addressDocManager.setCountry("newCountry");
    	System.out.println(addressDocManager.getCountry());
    	addressDocManager.setNumber("12A");
    	System.out.println(addressDocManager.getNumber());
    	addressDocManager.setPostalCode("3173 ZP");
    	System.out.println(addressDocManager.getPostalCode());
    	addressDocManager.setStreet("newstreet");
    	System.out.println(addressDocManager.getStreet());
    	
    	//Print and change game info
    	System.out.println("\ngameinfo:");
    	GameDocumentManager gameDocManager = new GameDocumentManager(gameCollectionManager.getGameDocument(1));
    	gameDocManager.setName("newGame");
    	System.out.println(gameDocManager.getName());
    	gameDocManager.setAge(12);
    	System.out.println(gameDocManager.getAge());
    	gameDocManager.setAmountInStock(4);
    	System.out.println(gameDocManager.getAmountInStock());
    	gameDocManager.setDate(new Date());
    	System.out.println(gameDocManager.getDate());
    	gameDocManager.setDescription("new game desc");
    	System.out.println(gameDocManager.getDescription());
    	gameDocManager.setPlatform("new platform");
    	System.out.println(gameDocManager.getPlatform());
    	gameDocManager.setPrice(30);
    	System.out.println(gameDocManager.getPrice());
    	gameDocManager.setGenre("new genre");
    	System.out.println(gameDocManager.getGenre());
    	System.out.println(gameDocManager.getPublisher());
    	
//    	gameCollectionManager.dropCollection();
//    	userCollectionManager.dropCollection();
    }
}