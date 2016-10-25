package controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

/**
 * Created by Dave on 24-10-16.
 */


public class RootLayoutController {

    /*//Reference to the main application
    private Main main;

    //Is called by the main application to give a reference back to itself.
    public void setMain (Main main) {
        this.main = main;
    }*/

    //Exit the program
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    //Help Menu button behavior
    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("Assignment 1");
        alert.setContentText("You can search, delete, update, insert a new employee with this program.");
        alert.show();
    }
}
