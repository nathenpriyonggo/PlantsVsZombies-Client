import java.util.ArrayList;
import java.util.Objects;

import com.sun.javafx.sg.prism.NGAmbientLight;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.util.Duration;


public class GuiClient extends Application{


	Client clientConnection;
	String clientName;
	TextField text_username;
	Button button_usernameConfirm;
	Label label_oppName, label_playerName, label_loading;
	GridPane gridPlayer, gridOpponent;
	ArrayList<Image> array_gifFrames;
	ImageView imgView_peaGif;
	Timeline timeline_peaGif;

	ArrayList<Element> array_oppElement, array_playerElement;
	int peaGif_frameIndex;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Discord-style fonts
		Font.loadFont(getClass().getResourceAsStream("Fonts/gg sans Medium.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("Fonts/gg sans Semibold.ttf"), 14);
		Font.loadFont(getClass().getResourceAsStream("Fonts/gg sans Bold.ttf"), 14);

		// Callback.accept ends up here
		clientConnection = new Client(data->{
			Platform.runLater(()->{
				Message msg = (Message) data;

				// Input message is respond to check unique name request from 'button_usernameConfirm'
				if (msg.flagIsCheckUniqueName()) {
					// If player name is unique, initiate setup for 'HomeGUI', else inform user error
					if (msg.usernameIsUnique()) {
						clientName = msg.getPlayerName();
						primaryStage.setTitle(clientName + "'s Plants Vs Zombies Battleships");
						primaryStage.setScene(GameplayGUI());
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
		text_username = new TextField("Enter your name!");
		text_username.setStyle(
				"-fx-font-family: 'gg sans Semibold';" +
				"-fx-font-size: 14;" +
				"-fx-text-fill: black;" +
				"-fx-max-width: 230;" +
				"-fx-alignment: center;");
		text_username.setOnKeyPressed(e-> {
			if (e.getCode() == KeyCode.ENTER) {
				button_usernameConfirm.fire();
			}
		});
		// Username Confirm Button
		button_usernameConfirm = new Button("Confirm");
		button_usernameConfirm.setStyle(
				"-fx-font-family: 'gg sans Semibold';" +
				"-fx-font-size: 14;" +
				"-fx-text-fill: white;" +
				"-fx-font-weight: bold;" +
				"-fx-background-color: #80D133");
		button_usernameConfirm.setOnAction(e-> {
			// If player name is blank inform user error, else send request to check unique name
			if (text_username.getText().isBlank())
			{
				text_username.setText("Username cannot be empty...");
			}
			else if (Objects.equals(text_username.getText(), "Username cannot be empty...") ||
					Objects.equals(text_username.getText(), "Username already exists...") ||
					Objects.equals(text_username.getText(), "Enter your name!") ||
					Objects.equals(text_username.getText(), "Find another username..."))
			{
				text_username.setText("Find another username...");
			}
			else
			{
				clientConnection.send(new Message(text_username.getText(),
						"", "flagIsCheckUniqueName"));
			}
		});



		/*
		Home GUI Scene Definitions
		 */



		/*
		Rules GUI Scene Definitions
		 */



		/*
		Placement GUI Scene Definitions
		 */



		/*
		Loading GUI Scene Definitions
		 */
		// Loading Label
		label_loading = new Label("Loading...");
		label_loading.setStyle(
				"-fx-font-family: 'gg sans Semibold';" +
						"-fx-font-size: 30;" +
						"-fx-text-fill: white;"
		);
		// Peashooter GIF ImageView
		array_gifFrames = new ArrayList<>();
		for (int i = 0; i <= 14; i ++) {
			Image frame = new Image("Plants/PeaGif/" + i + ".gif");
			array_gifFrames.add(frame);
		}
		peaGif_frameIndex = 0;
		imgView_peaGif = new ImageView(array_gifFrames.get(peaGif_frameIndex));
		imgView_peaGif.setFitHeight(200);
		imgView_peaGif.setFitWidth(200);
		timeline_peaGif = new Timeline(new KeyFrame(Duration.millis(80), e-> {
			peaGif_frameIndex = (peaGif_frameIndex + 1) % array_gifFrames.size();
			imgView_peaGif.setImage(array_gifFrames.get(peaGif_frameIndex));
		}));
		timeline_peaGif.setCycleCount(Animation.INDEFINITE);


		/*
		Gameplay GUI Scene Definitions
		 */

		// FIXME: delete after Placement GUI is made
		array_oppElement = new ArrayList<>(49);
		for (int i = 1; i <= 49; i++) {
			String img_url = "noFlag";
			int shipSize;
			int elementState = 1;
			if (i == 1 || i == 8 || i == 15) {
				shipSize = 3;
				img_url = "Zombies/dead_cone.png";
			}
			else if (i == 4 || i == 11 || i == 18 || i == 25) {
				shipSize = 4;
				img_url = "Zombies/dead_yeti.png";
			}
			else if (i == 29 || i == 30) {
				shipSize = 2;
				img_url = "Zombies/dead_zombie.png";
			}
			else if (38 <= i && i <= 40) {
				shipSize = 3;
				img_url = "Zombies/dead_bucket.png";
			}
			else if (i == 21 || i == 28 || i == 35 || i == 42 || i == 49) {
				shipSize = 5;
				img_url = "Zombies/dead_knight.png";
			}
			else {
				shipSize = 0;
				elementState = 0;
			}
			array_oppElement.add(new Element("Bot", clientName,
					(i-1) % 7,(i-1) / 7, shipSize, elementState, img_url, ""));
		}
		// FIXME: delete above

		// FIXME: delete after Placement GUI is made
		array_playerElement = new ArrayList<>(56);
		for (int i = 1; i <= 49; i++) {
			String img_url = "Plants/empty.png";
			int shipSize;
			int elementState = 1;
			if (i == 1 || i == 8 || i == 15) {
				shipSize = 3;
				img_url = "Plants/wallnut.png";
			}
			else if (i == 4 || i == 11 || i == 18 || i == 25) {
				shipSize = 4;
				img_url = "Plants/snowpea.png";
			}
			else if (i == 29 || i == 30) {
				shipSize = 2;
				img_url = "Plants/peashooter.png";
			}
			else if (38 <= i && i <= 40) {
				shipSize = 3;
				img_url = "Plants/sunflower.png";
			}
			else if (i == 21 || i == 28 || i == 35 || i == 42 || i == 49) {
				shipSize = 5;
				img_url = "Plants/chomper.png";
			}
			else {
				shipSize = 0;
				elementState = 0;
			}
			array_playerElement.add(new Element(clientName, "Bot",
					(i-1) % 7,(i-1) / 7, shipSize, elementState, img_url, ""));
		}
		// FIXME: delete above

		// Opponent's Name Label
		label_oppName = new Label("Bot");
		label_oppName.setStyle(
				"-fx-font-family: 'gg sans Bold';" +
						"-fx-font-size: 35;" +
						"-fx-text-fill: white;" +
						"-fx-alignment: TOP_LEFT;");

		// Opponent's Grid of buttons
		gridOpponent = new GridPane();
		gridOpponent.setPadding(new Insets(5));
		gridOpponent.setHgap(3);
		gridOpponent.setVgap(3);
		gridOpponent.setAlignment(Pos.CENTER);
		for (int i = 1; i <= array_oppElement.size(); i++) {

			Element elem = array_oppElement.get(i - 1);

			// Create zombie image from element's flag
			ImageView imgView = new ImageView(new Image("Zombies/empty.png"));
			imgView.setFitWidth(32);
			imgView.setFitHeight(32);
			imgView.setPreserveRatio(true);

			// Create new button for element with zombie image
			Button newButton = new Button();
			newButton.setGraphic(imgView);
			newButton.setStyle(
					"-fx-pref-tile-height: 50;" +
					"-fx-pref-tile-width: 50;"
			);

			// Set grid color to resemble chess pattern
			if ((elem.getX() + elem.getY()) % 2 == 0) {
				newButton.setStyle("-fx-background-color: #0E3B46");
			} else {
				newButton.setStyle("-fx-background-color: #114C59");
			}

			// Print to terminal button location
			newButton.setOnAction(e->{

				clientConnection.send(elem);
//				if (elem.getElementState() == 0) {
//					imgView.setImage(new Image("Zombies/miss.png"));
//				}
//				else if (elem.getElementState() == 1) {
//					imgView.setImage(new Image("Zombies/grave.png"));
//					elem.setElementState(2);
//				}
			});



			// Place 'newButton' in position
			GridPane.setColumnIndex(newButton, elem.getX());
			GridPane.setRowIndex(newButton, elem.getY());
			// Add 'newButton' to grid
			gridOpponent.getChildren().add(newButton);
		}

		Image newImg = new Image("Zombies/dead_yeti.png");
		ImageView newImgView = new ImageView(newImg);
		newImgView.setFitWidth(32);
		newImgView.setFitHeight(32);
		newImgView.setPreserveRatio(true);
		Button newButton = new Button();
		newButton.setGraphic(newImgView);
		GridPane.setColumnIndex(newButton, 1);
		GridPane.setRowIndex(newButton, 1);
		gridOpponent.getChildren().add(newButton);

		// Player's Name Label
		label_playerName = new Label("Player");
		label_playerName.setStyle(
				"-fx-font-family: 'gg sans Bold';" +
						"-fx-font-size: 35;" +
						"-fx-text-fill: white;" +
						"-fx-alignment: TOP_LEFT;");

		// Player's Grid of buttons
		gridPlayer = new GridPane();
		gridPlayer.setPadding(new Insets(5));
		gridPlayer.setHgap(3);
		gridPlayer.setVgap(3);
		gridPlayer.setAlignment(Pos.CENTER);
		for (int i = 1; i <= array_playerElement.size(); i++) {

			Element elem = array_playerElement.get(i - 1);

			// Create plant image from element's flag
			Image img = new Image(elem.getUrl());
			ImageView imgView = new ImageView(img);
			imgView.setFitWidth(32);
			imgView.setFitHeight(32);
			imgView.setPreserveRatio(true);

			// Create new button for element with plant image
			Button button = new Button();
			button.setGraphic(imgView);
			button.setStyle(
					"-fx-pref-tile-height: 50;" +
							"-fx-pref-tile-width: 50;");

			// Set grid color to resemble chess pattern
			if ((elem.getX() + elem.getY()) % 2 == 0) {
				button.setStyle("-fx-background-color: #02AA0E");
			} else {
				button.setStyle("-fx-background-color: #00D016");
			}

			// Print to terminal button location
			button.setOnAction(e->{
				System.out.println(elem.getPlayer() + elem.getX() + elem.getY());
			});

			// Place 'newButton' in position
			GridPane.setColumnIndex(button, elem.getX());
			GridPane.setRowIndex(button, elem.getY());
			// Add 'newButton' to grid
			gridPlayer.getChildren().add(button);
		}













		// Closing Game
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		// Setup initial scene
		Scene scene = UsernameGUI();
		primaryStage.setScene(scene);
		scene.getRoot().requestFocus();
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
		VBox vBox_center = new VBox(20, text_username, button_usernameConfirm);
		vBox_center.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setCenter(vBox_center);

		// Background Image
		BackgroundImage bgImage = new BackgroundImage(new Image("Backgrounds/bg_username.png"),
				null, null,
				null, null);
		Background bg = new Background(bgImage);
		pane.setBackground(bg);

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
		Label test = new Label("Gameplay");
		test.setStyle("-fx-font-family: Arial");
		pane.setCenter(test);

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
	Loading Match GUI Code
		~ displays pea-shooter with text letting user know matchmaking is in progress
		~ player can control pea-shooter during matchmaking (*animated), and shoot peas
		~ counter will increment on space button action
		~ loading screen will change when opponent has been found
	 */
	public Scene LoadingGUI()
	{
		timeline_peaGif.play();
		VBox vBox_center = new VBox(10, imgView_peaGif, label_loading);
		vBox_center.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50));
		pane.setCenter(vBox_center);

		// Background Image
		BackgroundImage bgImage = new BackgroundImage(new Image("Backgrounds/bg_loading.png"),
				null, null,
				null, null);
		Background bg = new Background(bgImage);
		pane.setBackground(bg);


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

		VBox vBox_top = new VBox(10, label_oppName, gridOpponent);

		VBox vBox_bot = new VBox(10, label_playerName, gridPlayer);


		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(30));
		pane.setTop(vBox_top);
		pane.setBottom(vBox_bot);

		// Background Image
		BackgroundImage bgImage = new BackgroundImage(new Image("Backgrounds/bg_gameplay.png"),
				null, null,
				null, null);
		Background bg = new Background(bgImage);
		pane.setBackground(bg);

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
		Label test = new Label("WINGUI");
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
