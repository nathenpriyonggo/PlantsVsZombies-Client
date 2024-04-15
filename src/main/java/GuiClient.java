import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class GuiClient extends Application{


	Client clientConnection;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Callback.accept ends up here
		clientConnection = new Client(data->{
			Platform.runLater(()->{
				// FIXME
			});
		});
		clientConnection.start();




		// ----------------------------- Element Definitions Below -----------------------------






		// Closing Game
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		// Setup initial scene
		primaryStage.setScene(UsernameGUI());
		primaryStage.setTitle("PlantShip - ZombieShip");
		primaryStage.show();
		
	}




	// ----------------------------- GUI Functions Below -----------------------------




	// TODO -->
	/*
	Username Page GUI Code
	 	~ displays title with username text field; center
		~ prompts user to input username
		~ button clicked to proceed, check if unique name
		~ if false, prompt user to input other name
		~ if true, proceed to home page
	 */
	public Scene UsernameGUI() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setStyle("-fx-background-color: white");

		return new Scene(pane, 800, 500);
	}

	/*
	TODO -->
	Home Page GUI Code
		~ displays username and money; top left
		~ displays rule page button; top right
		~ displays title and streaks; center
		~ option to random PvP or AI
		~ if no players available, send wait message
	 */
	public Scene HomeGUI() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setStyle("-fx-background-color: white");

		return new Scene(pane, 800, 500);
	}

	/*
	TODO -->
	Rules Page GUI Code
		~ displays rules; center
		~ back button changes scene to home page; top left
	 */
	public Scene RulesGUI() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setStyle("-fx-background-color: white");

		return new Scene(pane, 800, 500);
	}

	/*
	TODO -->
	Player's Plants Placement GUI Code
		~ displays tableview of buttons and 5 rows of buttons; center
		~ user clicks desired character button
		~ user clicks desired position
		~ if valid position, clicked buttons disabled
		~ if invalid, send error message
		~ after all characters placed, enable start button
	 */
	public Scene PlacementGUI() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setStyle("-fx-background-color: white");

		return new Scene(pane, 800, 500);
	}

	/*
	TODO -->
	Battleship Gameplay GUI Code
		~ displays opponent's tableview of buttons; top center
		~ displays player's tableview of imageview; bottom center
		~ user will be notified for turns
		~ user clicks on opponent's tableview to guess position
		~ user will be notified if opponent guessed user table correctly
	 */
	public Scene GameplayGUI() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setStyle("-fx-background-color: white");

		return new Scene(pane, 800, 500);
	}

	/*
	TODO -->
	Win Outcome GUI Code
		~ displays trophy and win text
		~ home button changes scene to home page
	 */
	public Scene WinGUI() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setStyle("-fx-background-color: white");

		return new Scene(pane, 800, 500);
	}

	/*
	TODO -->
	Lose Outcome GUI Code
		~ displays zombie arm and lose text
		~ home button changes scene to home page
	 */
	public Scene LoseGUI() {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setStyle("-fx-background-color: white");

		return new Scene(pane, 800, 500);
	}
}
