package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DataFiller;
import model.Database;
import model.collection.GameCollectionManager;
import model.collection.UserCollectionManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;


/**
 * Created by Dave on 24-10-16.
 */


//Main class which extends from main.Application Class
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
        Database database = Database.getInstance();
    	DataFiller filler = new DataFiller();
    	GameCollectionManager gameCollectionManager = new GameCollectionManager();
//    	gameCollectionManager.dropCollection();
        UserCollectionManager userCollectionManager = new UserCollectionManager();
//        userCollectionManager.dropCollection();
//
//    	int size = filler.getDataLines().size();
//    	System.out.println(size);
//    	for (int i = 1 ; i < size ; i++) {
//    		System.out.println(i);
//    		filler.addToDataMap(i);
//        	filler.insertGame(i - 1);
//    	}
    	
//    	//Insert two games in the game collection.
    	gameCollectionManager.insertNewGameDocument(gameCollectionManager.createGameDocument(1, "Game", "Awesome game", "EA", 12.22, 18, "PC", "Shooter", 10, new Date()));
    	gameCollectionManager.insertNewGameDocument(gameCollectionManager.createGameDocument(2, "Great Game", "Great game, play this 24/7", "Nintendo", 20.00, 7, "PS3", "Shooter", 2, new Date()));
//    	
//    	//Insert a new User in the User collection.
//   	userCollectionManager.insertUser("SuperUser12", "pass123", 18, new Date(), "SuperUser12@gmail.com", false, true, userCollectionManager.createAddressDocument("Netherlands", "Rotterdam", "Wijnhaven", "107", "1264 ZF"), Arrays.asList(userCollectionManager.createCartItemDocument(1)), Arrays.asList(), Arrays.asList(), Arrays.asList());
//    	
//    	//Print and change userinfo
//    	UserDocumentManager userDocManager = new UserDocumentManager(userCollectionManager.getUserDocument("SuperUser12"));
//    	userDocManager.setName("NewName12");
//    	userDocManager.setDateOfBirth(new Date());
//    	userDocManager.setEmail("test@gmail.com");
//    	userDocManager.setPassword("newpass123");
//    	userDocManager.setAge(20);
//    	userDocManager.setAdmin();
//    	userDocManager.addHistoryItem(1);
//    	userDocManager.addFavouriteItem(4);
////    	userDocManager.getFavouriteList().forEach(System.out::println);
//    	userDocManager.addWishItem(4);
////    	userDocManager.getWishList().forEach(System.out::println);
//    	userDocManager.addCartItem(2);
//    	for (Document item : userDocManager.getCartItems()) {
//    		CartItemDocumentManager cartManager = new CartItemDocumentManager(item);
//    		cartManager.setAmount(cartManager.getAmount() + 1);
//    	}
//    	
//    	//Print and change address info
//    	AddressDocumentManager addressDocManager = new AddressDocumentManager(userDocManager.getAddress());
//    	addressDocManager.setCity("newCity");
//    	addressDocManager.setCountry("newCountry");
//    	addressDocManager.setNumber("12A");
//    	addressDocManager.setPostalCode("3173 ZP");
//    	addressDocManager.setStreet("newstreet");
//    	userDocManager.setAddress(addressDocManager.getDocument());
//    	
//    	//Print and change game info
//    	GameDocumentManager gameDocManager = new GameDocumentManager(gameCollectionManager.getGameDocument(1));
//    	gameDocManager.setName("newGame");
//    	gameDocManager.setAge(12);
//    	gameDocManager.setAmountInStock(4);
//    	gameDocManager.setDate(new Date());
//    	gameDocManager.setDescription("new game desc");
//    	gameDocManager.setPlatform("new platform");
//    	gameDocManager.setPrice(30);
//    	gameDocManager.setGenre("new genre");
    	
//    	gameCollectionManager.dropCollection();
//    	userCollectionManager.dropCollection();
    }
}