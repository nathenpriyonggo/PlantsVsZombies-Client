import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class GuiClient extends Application{


	Client clientConnection;
	String clientName;
	TextField text_username;
	Button button_usernameConfirm;
	GridPane gridPane_placement;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Callback.accept ends up here
		clientConnection = new Client(data->{
			Platform.runLater(()->{
				Message msg = (Message) data;

				// Input message is respond to check unique name request from 'button_usernameConfirm'
				if (msg.flagIsCheckUniqueName()) {
					// If player name is unique, initiate setup for 'HomeGUI', else inform user error
					if (msg.usernameIsUnique()) {
						clientName = msg.getPlayerName();
						primaryStage.setTitle(clientName + "'s PlantShip - ZombieShip");
						primaryStage.setScene(HomeGUI());
						clientConnection.send(new Message(clientName,
								"", "flagIsNewClientJoined"));
					} else {
						text_username.setText("Username already exists...");
					}
				}
			});
		});
		clientConnection.start();




		// ----------------------------- Element Definitions Below -----------------------------

		/*
		Username GUI Scene Definitions
		 */
		// Username Text Field
		text_username = new TextField();
		text_username.setStyle("-fx-font-family: Arial;" +
				"-fx-font-size: 14;" +
				"-fx-text-fill: black;");
		text_username.setOnKeyPressed(e-> {
			if (e.getCode() == KeyCode.ENTER) {
				button_usernameConfirm.fire();
			}
		});
		// Username Confirm Button
		button_usernameConfirm = new Button("Confirm");
		button_usernameConfirm.setStyle("-fx-font-family: Arial;" +
				"-fx-font-size: 14;" +
				"-fx-text-fill: black;" +
				"-fx-background-color: white");
		button_usernameConfirm.setOnAction(e-> {
			// If player name is blank inform user error, else send request to check unique name
			if (text_username.getText().isBlank()) {
				text_username.setText("Username cannot be empty...");
			} else {
				clientConnection.send(new Message(text_username.getText(),
						"", "flagIsCheckUniqueName"));
			}
		});











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

		// Contains username text field (top) and username button (button)
		VBox vBox_center = new VBox(10, text_username, button_usernameConfirm);
		vBox_center.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setStyle("-fx-background-color: white");
		pane.setCenter(vBox_center);

		return new Scene(pane, 500, 800);
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

		return new Scene(pane, 500, 800);
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

		return new Scene(pane, 500, 800);
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
		Label test = new Label("Placement ");
		test.setStyle("-fx-font-family: Arial");
		pane.setCenter(test);

		return new Scene(pane, 500, 800);
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

		return new Scene(pane, 500, 800);
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
		Label test = new Label("WINGUI ");
		test.setStyle("-fx-font-family: Arial");
		pane.setCenter(test);

		return new Scene(pane, 500, 800);
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
		Label test = new Label("LOSEGUID ");
		test.setStyle("-fx-font-family: Arial");
		pane.setCenter(test);

		return new Scene(pane, 500, 800);
	}
}
