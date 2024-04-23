import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import com.sun.javafx.sg.prism.NGAmbientLight;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.util.Duration;


public class GuiClient extends Application{


    // ----------------------------- Variable Declarations Below -----------------------------

    Client clientConnection;
    String clientName, wantsToPlayAgainst, str_placementCurrShip;
    int int_sun, int_placementCurrShip, peaGif_frameIndex;;
    boolean[][] boolArr_isShipPlaced, boolArr_oppButtonsPressed;
    HashSet<String> hashSet_shipsPlaced = new HashSet<>();
    TextField text_username;
    Button button_usernameConfirm, button_homeRules, button_homePvP, button_homeAI, button_rulesBack,
            button_placementStart, button_placementEnd, button_placementPea, button_placementSun,
            button_placementWall, button_placementSnow, button_placementChomp,
            button_placementStartGame, button_winHome, button_loseHome;
    Button[][] buttons_placement, buttons_opponent, buttons_player;
    Label label_oppName, label_playerName, label_loading, label_homeName, label_homeSun, label_notification;
    GridPane gridPlacement, gridPlayer, gridOpponent;
    ArrayList<Image> array_gifFrames;
    ImageView imgView_peaGif, imgView_battleIcon, imgView_AIIcon, imgView_profile, imgView_sun,
            imgView_howTo;
    Timeline timeline_peaGif;
    Ships ships_player = new Ships();
    Ships ships_opponent = new Ships();
    ImageView[][] imageViews_players;



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

                System.out.println(data.getClass());
                // Type 'Message' input
                if (data.getClass().toString().equals("class Message")) {
                    Message msg = (Message) data;

                    // Input message is respond to check unique name request from 'button_usernameConfirm'
                    if (msg.flagIsCheckUniqueName()) {
                        // If player name is unique, initiate setup for 'HomeGUI', else inform user error
                        if (msg.usernameIsUnique()) {
                            clientName = msg.getPlayerName();
                            primaryStage.setScene(HomeGUI());
                            clientConnection.send(new Message(clientName,
                                    "", "flagIsNewClientJoined"));
                        } else {
                            text_username.setText("Username already exists...");
                        }
                    }
                    // Input message is notification that client won, auto win
                    else if (msg.flagIsClientWon()) {
                        helperFunc_placementGridInitialization();
                        helperFunc_gameplayGridInitializations();
                        primaryStage.setScene(WinGUI());
                    }
                    // Input message is notification that client lost, auto lost
                    else if (msg.flagIsClientLost()) {
                        helperFunc_placementGridInitialization();
                        helperFunc_gameplayGridInitializations();
                        primaryStage.setScene(LoseGUI());
                    }
                    // Input message is notification to start game on our turn
                    else if (msg.flagIsStartGameYourTurn()) {
                        label_loading.setText("Found Opponent: " +
                                ships_player.opponentName + "!");
                        label_notification.setText("Your Turn!");

                        // Pause for 3 seconds, then start 'GameplayGUI()'
                        PauseTransition delay = new PauseTransition(Duration.seconds(3));
                        delay.setOnFinished(e-> {
                            primaryStage.setScene(GameplayGUI());
                        });
                        delay.play();
                    }
                    // Input message is notification to start game on opponent turn
                    else if (msg.flagIsStartGameOppTurn()) {
                        label_loading.setText("Found Opponent: " +
                                ships_player.opponentName + "!");
                        label_notification.setText("Opponent's Turn!");
                        // Disable all buttons since opponent's turn
                        for (int i = 0; i < 7; i++) {
                            for (int j = 0; j < 7; j++) {
                                buttons_opponent[i][j].setDisable(true);
                            }
                        }
                        // Pause for 3 seconds, then start 'GameplayGUI()'
                        PauseTransition delay = new PauseTransition(Duration.seconds(3));
                        delay.setOnFinished(e-> {
                            primaryStage.setScene(GameplayGUI());
                        });
                        delay.play();
                    }
                }
                // Type 'Element' input
                else if (data.getClass().toString().equals("class Element")) {

                    Element elem = (Element) data;
                    Element newElem = ships_player.didItHitPlant(elem.getX(), elem.getY());
                    ImageView newImageView = new ImageView(new Image(newElem.getUrl()));
                    newImageView.setFitWidth(32);
                    newImageView.setFitHeight(32);
                    newImageView.setPreserveRatio(true);
                    buttons_player[newElem.getY()][newElem.getX()].setGraphic(newImageView);

                    if (newElem.getElementState() == 0) {
                        label_notification.setText("Opponent missed! - Your Turn!");
                    }
                    else {
                        label_notification.setText("Plant got Hit! - Your Turn!");
                    }

                    // Disable only non-pressed buttons
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 7; j++) {
                            if (!boolArr_oppButtonsPressed[i][j]) {
                                buttons_opponent[i][j].setDisable(false);
                            }
                        }
                    }
                }
                // Type 'Ships' input
                else if (data.getClass().toString().equals("class Ships")) {
                    Ships ships = (Ships) data;

                    System.out.println("Got here in shipssss");
                    // Input message is initial player ships class
                    if (Objects.equals(ships.playerName, clientName)) {
                        ships_player = ships;
                        helperFunc_initializeImageViewArrayPlayers();
                    }
                    // Input message is initial opponent ships class
                    else if (Objects.equals(ships.opponentName, clientName)) {
                        ships_opponent = ships;
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
                        "-fx-alignment: center;"
        );
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
                        "-fx-background-color: #80D133;"
        );
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
        // Name Label
        label_homeName = new Label();
        label_homeName.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 25;" +
                        "-fx-text-fill: white;"
        );
        // Profile Image Icon
        imgView_profile = new ImageView(new Image("Icons/profile.png"));
        imgView_profile.setPreserveRatio(true);
        imgView_profile.setFitHeight(50);
        imgView_profile.setFitWidth(50);
        // Sun Label
        int_sun = 0;
        label_homeSun = new Label();
        label_homeSun.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 25;" +
                        "-fx-text-fill: white;"
        );
        // Sun Image Icon
        imgView_sun = new ImageView(new Image("Icons/sun.png"));
        imgView_sun.setPreserveRatio(true);
        imgView_sun.setFitHeight(50);
        imgView_sun.setFitWidth(50);
        // Rules Button
        button_homeRules = new Button("Rules");
        button_homeRules.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 20;" +
                        "-fx-text-fill: black;" +
                        "-fx-alignment: center;" +
                        "-fx-max-width: 150;" +
                        "-fx-background-color: #ECFFDC;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 3;" +
                        "-fx-border-width: 1;"
        );
        button_homeRules.setOnAction(e-> {
            primaryStage.setScene(RulesGUI());
        });
        // Battle Player Button
        button_homePvP = new Button("Battle Online!");
        button_homePvP.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 20;" +
                        "-fx-text-fill: white;" +
                        "-fx-alignment: center;" +
                        "-fx-max-width: 230;" +
                        "-fx-background-color: #50C878;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 3;" +
                        "-fx-border-width: 1;"
        );
        button_homePvP.setOnAction(e-> {
            wantsToPlayAgainst = "Player";
            primaryStage.setScene(PlacementGUI());
        });
        // Battle Icon
        imgView_battleIcon = new ImageView(new Image("Icons/battle.png"));
        imgView_battleIcon.setPreserveRatio(true);
        imgView_battleIcon.setFitHeight(40);
        imgView_battleIcon.setFitWidth(40);
        // Battle AI Button
        button_homeAI = new Button("Battle Offline!");
        button_homeAI.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 20;" +
                        "-fx-text-fill: white;" +
                        "-fx-alignment: center;" +
                        "-fx-max-width: 230;" +
                        "-fx-background-color: #2E8B57;"+
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 3;" +
                        "-fx-border-width: 1;"
        );
        button_homeAI.setOnAction(e-> {
            wantsToPlayAgainst = "AI";
            primaryStage.setScene(PlacementGUI());
//            primaryStage.setScene(new PlacementPage(primaryStage).getScene());
        });
        // AI Icon
        imgView_AIIcon = new ImageView(new Image("Icons/ai.png"));
        imgView_AIIcon.setPreserveRatio(true);
        imgView_AIIcon.setFitHeight(40);
        imgView_AIIcon.setFitWidth(40);




		/*
		Rules GUI Scene Definitions
		 */
        button_rulesBack = new Button("Back");
        button_rulesBack.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 16;" +
                        "-fx-text-fill: black;" +
                        "-fx-background-color: white;" +
                        "-fx-max-width: 200;" +
                        "-fx-alignment: center;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 3;" +
                        "-fx-border-width: 1;"
        );
        button_rulesBack.setOnAction(e-> {
            primaryStage.setScene(HomeGUI());
        });
        button_rulesBack.setAlignment(Pos.CENTER);






		/*
		Placement GUI Scene Definitions
		 */

        helperFunc_placementGridInitialization();
        // Pea shooter Placement Button
        button_placementPea = new Button();
        button_placementPea.setStyle(
                "-fx-background-image: url('Icons/peaButton.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-pref-width: 120;" +
                        "-fx-pref-height: 48;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 1;");
        button_placementPea.setOnAction(e-> {
            // Placement started, enable buttons
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (!boolArr_isShipPlaced[i][j]) {
                        buttons_placement[i][j].setDisable(false);
                    }
                }
            }
            // Set variables based on button
            int_placementCurrShip = 2;
            str_placementCurrShip = "Pea Shooter";
        });
        // Sunflower Placement Button
        button_placementSun = new Button();
        button_placementSun.setStyle(
                "-fx-background-image: url('Icons/sunButton.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-pref-width: 120;" +
                        "-fx-pref-height: 48;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 1;");
        button_placementSun.setOnAction(e-> {
            // Placement started, enable buttons
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (!boolArr_isShipPlaced[i][j]) {
                        buttons_placement[i][j].setDisable(false);
                    }
                }
            }
            // Set variables based on button
            int_placementCurrShip = 3;
            str_placementCurrShip = "Sunflower";
        });
        // Wall nut Placement Button
        button_placementWall = new Button();
        button_placementWall.setStyle(
                "-fx-background-image: url('Icons/wallButton.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-pref-width: 120;" +
                        "-fx-pref-height: 48;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 1;");
        button_placementWall.setOnAction(e-> {
            // Placement started, enable buttons
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (!boolArr_isShipPlaced[i][j]) {
                        buttons_placement[i][j].setDisable(false);
                    }
                }
            }
            // Set variables based on button
            int_placementCurrShip = 3;
            str_placementCurrShip = "Wall-Nut";
        });
        // Snow pea Placement Button
        button_placementSnow = new Button();
        button_placementSnow.setStyle(
                "-fx-background-image: url('Icons/snowButton.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-pref-width: 120;" +
                        "-fx-pref-height: 48;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 1;");
        button_placementSnow.setOnAction(e-> {
            // Placement started, enable buttons
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (!boolArr_isShipPlaced[i][j]) {
                        buttons_placement[i][j].setDisable(false);
                    }
                }
            }
            // Set variables based on button
            int_placementCurrShip = 4;
            str_placementCurrShip = "Snow Pea";
        });
        // Chomper Placement Button
        button_placementChomp = new Button();
        button_placementChomp.setStyle(
                "-fx-background-image: url('Icons/chompButton.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-pref-width: 120;" +
                        "-fx-pref-height: 48;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 1;");
        button_placementChomp.setOnAction(e-> {
            // Placement started, enable buttons
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (!boolArr_isShipPlaced[i][j]) {
                        buttons_placement[i][j].setDisable(false);
                    }
                }
            }
            // Set variables based on button
            int_placementCurrShip = 5;
            str_placementCurrShip = "Chomper";
        });
        // Placement Start Game Button
        button_placementStartGame = new Button("Start Game");
        button_placementStartGame.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 20;" +
                        "-fx-text-fill: white;" +
                        "-fx-alignment: center;" +
                        "-fx-max-width: 230;" +
                        "-fx-background-color: #50C878;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 3;" +
                        "-fx-border-width: 1;"
        );
        button_placementStartGame.setDisable(true);
        button_placementStartGame.setOnAction(e-> {
            ships_player.playerName = clientName;
            ships_player.opponentName = wantsToPlayAgainst;
            clientConnection.send(ships_player);
            primaryStage.setScene(LoadingGUI());
        });
        // How to Place Tutorial Image View
        imgView_howTo = new ImageView(new Image("Icons/howToPlace.png"));
        imgView_howTo.setFitHeight(252);
        imgView_howTo.setFitWidth(240);
        imgView_howTo.setPreserveRatio(true);











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
        // Notification Label
        label_notification = new Label();
        label_notification.setStyle(
                "-fx-font-family: 'gg sans Bold';" +
                        "-fx-font-size: 20;" +
                        "-fx-text-fill: white;"
        );
        label_notification.setAlignment(Pos.TOP_RIGHT);
        // Opponent's Name Label
        label_oppName = new Label("");
        label_oppName.setStyle(
                "-fx-font-family: 'gg sans Bold';" +
                        "-fx-font-size: 35;" +
                        "-fx-text-fill: white;" +
                        "-fx-alignment: TOP_LEFT;"
        );

        helperFunc_gameplayGridInitializations();


        // Player's Name Label
        label_playerName = new Label("");
        label_playerName.setStyle(
                "-fx-font-family: 'gg sans Bold';" +
                        "-fx-font-size: 35;" +
                        "-fx-text-fill: white;" +
                        "-fx-alignment: TOP_LEFT;"
        );







        /*
		Win GUI Scene Definitions
		 */
        button_winHome = new Button("Home");
        button_winHome.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 16;" +
                        "-fx-text-fill: black;" +
                        "-fx-background-color: white;" +
                        "-fx-max-width: 100;" +
                        "-fx-alignment: center;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 3;" +
                        "-fx-border-width: 1;"
        );
        button_winHome.setOnAction(e-> {
            int_sun += 100;
            label_loading.setText("Loading");
            primaryStage.setScene(HomeGUI());
        });









        /*
		Lose GUI Scene Definitions
		 */
        button_loseHome = new Button("Home");
        button_loseHome.setStyle(
                "-fx-font-family: 'gg sans Semibold';" +
                        "-fx-font-size: 16;" +
                        "-fx-text-fill: black;" +
                        "-fx-background-color: white;" +
                        "-fx-max-width: 100;" +
                        "-fx-alignment: center;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 3;" +
                        "-fx-border-width: 1;"
        );
        button_loseHome.setOnAction(e-> {
            int_sun -= 50;
            label_loading.setText("");
            primaryStage.setScene(HomeGUI());
        });
        button_loseHome.setAlignment(Pos.CENTER);








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
        primaryStage.setResizable(false);
        primaryStage.show();

    }






    // ----------------------------- Helper Functions Below -----------------------------

    // Shows alert based on 'message' being passed as argument
    public void helperFunc_showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Set custom font for the content text
        Font customFont = Font.font("gg sans Semibold", 14);
        // Change "Arial" to the desired font family and 14 to the desired font size
        alert.getDialogPane().setStyle("-fx-font-family: '" + customFont.getFamily()
                + "'; -fx-font-size: " + customFont.getSize() + "px;");
        alert.showAndWait();
    }

    // Confirms placement of ship
    public void helperFunc_placeShip() {

        // Reset styles for the next use
        button_placementStart.setBorder(new Border(new BorderStroke(
                Color.web("#DC143C"), BorderStrokeStyle.SOLID,
                null, new BorderWidths(0))));
        button_placementEnd.setBorder(new Border(new BorderStroke(
                Color.web("#DC143C"), BorderStrokeStyle.SOLID,
                null, new BorderWidths(0))));

        // Check if ship has already been placed
        if (hashSet_shipsPlaced.contains(str_placementCurrShip)) {
            helperFunc_showAlert("This plant has already been placed. Choose another plant.");
            // Reset styles for the next use
            helperFunc_resetButtons();
            return;
        }

        int startRow = GridPane.getRowIndex(button_placementStart);
        int startCol = GridPane.getColumnIndex(button_placementStart);
        int endRow = GridPane.getRowIndex(button_placementEnd);
        int endCol = GridPane.getColumnIndex(button_placementEnd);

        // Determine orientation and check if valid
        boolean isVertical = startCol == endCol;
        boolean isHorizontal = startRow == endRow;
        int size = int_placementCurrShip - 1;

        // Check if ship is placed in an invalid position
        if (!(isVertical || isHorizontal) || (isVertical && Math.abs(endRow - startRow) != size)
                || (isHorizontal && Math.abs(endCol - startCol) != size)) {
            helperFunc_showAlert("Invalid placement. " +
                    "Only place your plants next to each other horizontally or vertically.");
            helperFunc_resetButtons();
            return;
        }


        // Check for existing ships in placement area
        if (isVertical) {
            for (int row = Math.min(startRow, endRow); row <= Math.max(startRow, endRow); row++) {
                if (boolArr_isShipPlaced[row][startCol]) {
                    helperFunc_showAlert("Invalid placement. " +
                            "There is already another plant here.");
                    helperFunc_resetButtons();
                    return;
                }
            }
            // Place ship vertically
            for (int row = Math.min(startRow, endRow); row <= Math.max(startRow, endRow); row++) {

                // Create plant image
                ImageView imgView = new ImageView(new Image(helperFunc_retUrl()));
                imgView.setFitWidth(32);
                imgView.setFitHeight(32);
                imgView.setPreserveRatio(true);
                // Overwrite graphic for buttons
                buttons_placement[row][startCol].setGraphic(imgView);
                buttons_placement[row][startCol].setDisable(true);
                boolArr_isShipPlaced[row][startCol] = true;
                imageViews_players[row][startCol] = imgView;
                // Add to player ships class
                if (Objects.equals(str_placementCurrShip, "Pea Shooter")) {
                    ships_player.addNodeToPea(new Element(startCol, row, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
                else if (Objects.equals(str_placementCurrShip, "Sunflower")) {
                    ships_player.addNodeToSun(new Element(startCol, row, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
                else if (Objects.equals(str_placementCurrShip, "Wall-Nut")) {
                    ships_player.addNodeToWall(new Element(startCol, row, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
                else if (Objects.equals(str_placementCurrShip, "Snow Pea")) {
                    ships_player.addNodeToSnow(new Element(startCol, row, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
                else if (Objects.equals(str_placementCurrShip, "Chomper")) {
                    ships_player.addNodeToChomp(new Element(startCol, row, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
            }
        } else {
            for (int col = Math.min(startCol, endCol); col <= Math.max(startCol, endCol); col++) {
                if (boolArr_isShipPlaced[startRow][col]) {
                    helperFunc_showAlert("Invalid placement. " +
                            "There is already another plant here.");
                    helperFunc_resetButtons();
                    return;
                }
            }
            // Place ship horizontally
            for (int col = Math.min(startCol, endCol); col <= Math.max(startCol, endCol); col++) {

                // Create plant image
                ImageView imgView = new ImageView(new Image(helperFunc_retUrl()));
                imgView.setFitWidth(32);
                imgView.setFitHeight(32);
                imgView.setPreserveRatio(true);
                // Overwrite graphic for buttons
                buttons_placement[startRow][col].setGraphic(imgView);
                buttons_placement[startRow][col].setDisable(true);
                boolArr_isShipPlaced[startRow][col] = true;
                imageViews_players[startRow][col] = imgView;
                // Add to player ships class
                if (Objects.equals(str_placementCurrShip, "Pea Shooter")) {
                    ships_player.addNodeToPea(new Element(col, startRow, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
                else if (Objects.equals(str_placementCurrShip, "Sunflower")) {
                    ships_player.addNodeToSun(new Element(col, startRow, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
                else if (Objects.equals(str_placementCurrShip, "Wall-Nut")) {
                    ships_player.addNodeToWall(new Element(col, startRow, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
                else if (Objects.equals(str_placementCurrShip, "Snow Pea")) {
                    ships_player.addNodeToSnow(new Element(col, startRow, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
                else if (Objects.equals(str_placementCurrShip, "Chomper")) {
                    ships_player.addNodeToChomp(new Element(col, startRow, int_placementCurrShip,
                            1, helperFunc_retUrl(), ""));
                }
            }
        }

        // Disable placement buttons after found
        if (Objects.equals(str_placementCurrShip, "Pea Shooter")) {
            button_placementPea.setDisable(true);
        } else if (Objects.equals(str_placementCurrShip, "Sunflower")) {
            button_placementSun.setDisable(true);
        } else if (Objects.equals(str_placementCurrShip, "Wall-Nut")) {
            button_placementWall.setDisable(true);
        } else if (Objects.equals(str_placementCurrShip, "Snow Pea")) {
            button_placementSnow.setDisable(true);
        } else if (Objects.equals(str_placementCurrShip, "Chomper")) {
            button_placementChomp.setDisable(true);
        }
        // Add added plant to 'hashSet_shipsPlaced'
        hashSet_shipsPlaced.add(str_placementCurrShip);
        helperFunc_resetButtons();
        // Automatically move to the next ship size or indicate placement is done
        if (hashSet_shipsPlaced.size() == 5) {
            helperFunc_showAlert("All plants placed! Press the start button to find a match!");
            button_placementStartGame.setDisable(false);
        } else {
            int_placementCurrShip++;
        }
    }

    // Sets null values to 'button_placementStart' and 'button_placementEnd'
    public void helperFunc_resetButtons() {
        button_placementStart = null;
        button_placementEnd = null;
        // Placement ended, disable buttons
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                buttons_placement[i][j].setDisable(true);
            }
        }
    }

    // Returns url string of image address based on 'str_placementCurrShip';
    public String helperFunc_retUrl() {
        switch (str_placementCurrShip) {
            case "Pea Shooter": return "Plants/peashooter.png";
            case "Sunflower": return "Plants/sunflower.png";
            case "Wall-Nut": return "Plants/wallnut.png";
            case "Snow Pea": return "Plants/snowpea.png";
            case "Chomper": return "Plants/chomper.png";
            default: return null;  // null, should not happen
        }
    }

    // Updates graphics on buttons if needed to convert from grave to zombie
    public void helperFunc_updateOpponentButtonsIfSunk(Element elem) {

        // Nothing needed to update
        if (elem.getElementState() == 0) {
            return;
        }
        else {
            // Check if needed to update grave to regular zombie
            if (ships_opponent.isPeaSunk() && !ships_opponent.peaShip.shown) {
                for (int i = 0; i < 2; i++) {
                    Element peaElem = ships_opponent.peaShip.next();
                    System.out.println(peaElem.getY() + peaElem.getX());
                    ImageView newImgView = new ImageView();
                    newImgView.setFitHeight(32);
                    newImgView.setFitWidth(32);
                    newImgView.setPreserveRatio(true);
                    newImgView.setImage(new Image(elem.getUrl()));
                    buttons_opponent[peaElem.getY()][peaElem.getX()].setGraphic(newImgView);
                }
                ships_opponent.peaShip.shown = true;
            }
            // Check if needed to update grave to cone zombie
            else if (ships_opponent.isSunSunk() && !ships_opponent.sunShip.shown) {
                for (int i = 0; i < 3; i++) {
                    Element sunElem = ships_opponent.sunShip.next();
                    ImageView newImgView = new ImageView();
                    newImgView.setFitHeight(32);
                    newImgView.setFitWidth(32);
                    newImgView.setPreserveRatio(true);
                    newImgView.setImage(new Image(elem.getUrl()));
                    buttons_opponent[sunElem.getY()][sunElem.getX()].setGraphic(newImgView);
                }
                ships_opponent.sunShip.shown = true;
            }
            // Check if needed to update grave to bucket zombie
            else if (ships_opponent.isWallSunk() && !ships_opponent.wallShip.shown) {
                for (int i = 0; i < 3; i++) {
                    Element wallElem = ships_opponent.wallShip.next();
                    ImageView newImgView = new ImageView();
                    newImgView.setFitHeight(32);
                    newImgView.setFitWidth(32);
                    newImgView.setPreserveRatio(true);
                    newImgView.setImage(new Image(elem.getUrl()));
                    buttons_opponent[wallElem.getY()][wallElem.getX()].setGraphic(newImgView);
                }
                ships_opponent.wallShip.shown = true;
            }
            // Check if needed to update grave to knight zombie
            else if (ships_opponent.isSnowSunk() && !ships_opponent.snowShip.shown) {
                for (int i = 0; i < 4; i++) {
                    Element snowElem = ships_opponent.snowShip.next();
                    ImageView newImgView = new ImageView();
                    newImgView.setFitHeight(32);
                    newImgView.setFitWidth(32);
                    newImgView.setPreserveRatio(true);
                    newImgView.setImage(new Image(elem.getUrl()));
                    buttons_opponent[snowElem.getY()][snowElem.getX()].setGraphic(newImgView);
                }
                ships_opponent.snowShip.shown = true;
            }
            // Check if needed to update grave to yeti zombie
            else if (ships_opponent.isChompSunk() && !ships_opponent.chompShip.shown) {
                for (int i = 0; i < 5; i++) {
                    Element chompElem = ships_opponent.chompShip.next();
                    ImageView newImgView = new ImageView();
                    newImgView.setFitHeight(32);
                    newImgView.setFitWidth(32);
                    newImgView.setPreserveRatio(true);
                    newImgView.setImage(new Image(elem.getUrl()));
                    buttons_opponent[chompElem.getY()][chompElem.getX()].setGraphic(newImgView);
                }
                ships_opponent.chompShip.shown = true;
            }
            // Nothing needed to update
            else {
                return;
            }
        }
    }

    // Initialize imageview array of players in gameplay
    public void helperFunc_initializeImageViewArrayPlayers() {
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                // Create plant image from array 'imageViews_players'
                Button button = new Button();
                button.setGraphic(imageViews_players[row][col]);
                // Set background color to resemble chess pattern
                if ((row + col) % 2 == 0) {
                    button.setStyle("-fx-background-color: #02AA0E");
                } else {
                    button.setStyle("-fx-background-color: #00D016");
                }
                gridPlayer.add(button, col, row);
                buttons_player[row][col] = button;
            }
        }
    }

    // Initializations for placement GUI
    public void helperFunc_placementGridInitialization() {
        // Variable Initializations
        buttons_placement = new Button[7][7];
        boolArr_isShipPlaced = new boolean[7][7];
        hashSet_shipsPlaced = new HashSet<>();
        int_placementCurrShip = 2;
        str_placementCurrShip = "Pea Shooter";
        button_placementStart = null;
        button_placementEnd = null;
        imageViews_players = new ImageView[7][7];
        ships_player = new Ships();
        ships_opponent = new Ships();
        // Construct Grid
        gridPlacement = new GridPane();
        gridPlacement.setHgap(3);
        gridPlacement.setVgap(3);
        gridPlacement.setAlignment(Pos.CENTER);
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                // Create plant image
                ImageView imgView = new ImageView(new Image("Plants/empty.png"));
                imgView.setFitWidth(32);
                imgView.setFitHeight(32);
                imgView.setPreserveRatio(true);
                // Construct Button
                Button button = new Button();
                button.setPrefSize(32, 32);
                button.setGraphic(imgView);
                // Set background color to resemble chess pattern
                if ((row + col) % 2 == 0) {
                    button.setStyle("-fx-background-color: #02AA0E");
                } else {
                    button.setStyle("-fx-background-color: #00D016");
                }
                // Set trigger action
                button.setOnAction(e-> {
                    if (button_placementStart == null) {
                        button_placementStart = button;
                        button_placementStart.setBorder(new Border(new BorderStroke(
                                Color.web("#DC143C"), BorderStrokeStyle.SOLID,
                                null, new BorderWidths(1))));
                    }
                    else if (button_placementEnd == null && button != button_placementStart) {
                        button_placementEnd = button;
                        helperFunc_placeShip();
                    }
                });
                // Initialize disabled button
                button.setDisable(true);
                // Add button to 2D array of 'buttons_placement' and 'gridPlacement'
                gridPlacement.add(button, col, row);
                buttons_placement[row][col] = button;
                imageViews_players[row][col] = imgView;
            }
        }
    }

    // Initializations for gameplay
    public void helperFunc_gameplayGridInitializations() {
        // Variable Initializations
        buttons_opponent = new Button[7][7];
        boolArr_oppButtonsPressed = new boolean[7][7];
        // Opponent's Grid of buttons
        gridOpponent = new GridPane();
        gridOpponent.setHgap(3);
        gridOpponent.setVgap(3);
        gridOpponent.setAlignment(Pos.CENTER);
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                // Create zombie image
                ImageView imgView = new ImageView(new Image("Zombies/empty.png"));
                imgView.setFitWidth(32);
                imgView.setFitHeight(32);
                imgView.setPreserveRatio(true);
                // Construct Button
                Button button = new Button();
                button.setPrefSize(32, 32);
                button.setGraphic(imgView);
                // Set background color to resemble chess pattern
                if ((row + col) % 2 == 0) {
                    button.setStyle("-fx-background-color: #0E3B46");
                } else {
                    button.setStyle("-fx-background-color: #114C59");
                }
                // Set trigger action
                int currCol = col;
                int currRow = row;
                button.setOnAction(e-> {
                    Element elem = ships_opponent.didItHitZombie(currCol, currRow);
                    ImageView newImageView = new ImageView();
                    newImageView.setFitWidth(32);
                    newImageView.setFitHeight(32);
                    newImageView.setPreserveRatio(true);
                    // Missed, show miss image
                    if (elem.getElementState() == 0) {
                        newImageView.setImage(new Image("Zombies/miss.png"));
                        label_notification.setText("You Missed! - Opponent's Turn");
                    }
                    else {
                        newImageView.setImage(new Image("Zombies/grave.png"));
                        label_notification.setText("Zombie Hit! - Opponent's Turn");
                    }
                    // Set respective variables
                    buttons_opponent[currRow][currCol].setGraphic(newImageView);
                    boolArr_oppButtonsPressed[currRow][currCol] = true;
                    // Update grave to zombies if needed
                    helperFunc_updateOpponentButtonsIfSunk(elem);
                    // Reset all buttons to be disabled, wait for opponent to finish turn
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 7; j++) {
                            buttons_opponent[i][j].setDisable(true);
                        }
                    }
                    // Send server client's choice to update opponent's table
                    clientConnection.send(elem);
                });
                // Add button to 2D array of 'buttons_opponent' and 'gridOpponent
                buttons_opponent[row][col] = button;
                gridOpponent.add(button, col, row);
            }
        }
        // Variable Initializations
        buttons_player = new Button[7][7];
        // Player's Grid of buttons
        gridPlayer = new GridPane();
        gridPlayer.setHgap(3);
        gridPlayer.setVgap(3);
        gridPlayer.setAlignment(Pos.CENTER);
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

        label_homeName.setText(clientName);
        label_homeSun.setText(String.valueOf(int_sun));

        HBox hBox_profile = new HBox(10, imgView_profile, label_homeName);
        hBox_profile.setAlignment(Pos.CENTER_LEFT);

        HBox hBox_sun = new HBox(10, imgView_sun, label_homeSun);
        hBox_sun.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(hBox_sun, javafx.scene.layout.Priority.ALWAYS);

        HBox hBox_top = new HBox(hBox_profile, hBox_sun);
        hBox_top.setAlignment(Pos.CENTER);
        hBox_top.setSpacing(20);

        VBox vBox_PvP = new VBox(5, button_homePvP, imgView_battleIcon);
        vBox_PvP.setAlignment(Pos.CENTER);

        VBox vBox_AI = new VBox(5, button_homeAI, imgView_AIIcon);
        vBox_AI.setAlignment(Pos.CENTER);

        HBox hBox_battleButtons = new HBox(50, vBox_PvP, vBox_AI);
        hBox_battleButtons.setAlignment(Pos.CENTER);

        VBox vBox_buttons = new VBox(40, button_homeRules, hBox_battleButtons);
        vBox_buttons.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(40, 50, 70, 50));
        pane.setBottom(vBox_buttons);
        pane.setTop(hBox_top);

        // Background Image
        BackgroundImage bgImage = new BackgroundImage(new Image("Backgrounds/bg_home.png"),
                null, null,
                null, null);
        Background bg = new Background(bgImage);
        pane.setBackground(bg);

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
        pane.setPadding(new Insets(70));
        pane.setBottom(button_rulesBack);

        // Background Image
        BackgroundImage bgImage = new BackgroundImage(new Image("Backgrounds/bg_rules.png"),
                null, null,
                null, null);
        Background bg = new Background(bgImage);
        pane.setBackground(bg);

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


        // Buttons Initializations
        button_placementPea.setDisable(false);
        button_placementSun.setDisable(false);
        button_placementWall.setDisable(false);
        button_placementSnow.setDisable(false);
        button_placementChomp.setDisable(false);

        VBox vBox_buttons = new VBox(7, button_placementPea, button_placementSun,
                button_placementWall, button_placementSnow, button_placementChomp);
        vBox_buttons.setAlignment(Pos.CENTER);

        HBox hBox_buttonsAndTutorial = new HBox(20, vBox_buttons, imgView_howTo);
        hBox_buttonsAndTutorial.setAlignment(Pos.CENTER);

        VBox vBox_top = new VBox(70, gridPlacement, hBox_buttonsAndTutorial);
        vBox_top.setAlignment(Pos.CENTER_RIGHT);

        VBox vBox_center = new VBox(20, vBox_top, button_placementStartGame);
        vBox_center.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(50, 30, 30, 30));
        pane.setTop(vBox_center);

        // Background Image
        BackgroundImage bgImage = new BackgroundImage(new Image("Backgrounds/bg_placement.png"),
                null, null,
                null, null);
        Background bg = new Background(bgImage);
        pane.setBackground(bg);

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



        label_playerName.setText(clientName);
        label_oppName.setText(ships_player.opponentName);


        VBox vBox_top = new VBox(10, label_oppName, gridOpponent);

        HBox hBox_playerNameAndNotification = new HBox(20, label_playerName,
                label_notification);

        VBox vBox_bot = new VBox(10, hBox_playerNameAndNotification, gridPlayer);

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
        pane.setPadding(new Insets(0, 0, 160, 0));
        BorderPane.setAlignment(button_winHome, Pos.CENTER);
        pane.setBottom(button_winHome);

        // Background Image
        BackgroundImage bgImage = new BackgroundImage(new Image("Backgrounds/bg_win.png"),
                null, null,
                null, null);
        Background bg = new Background(bgImage);
        pane.setBackground(bg);

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
        BorderPane.setAlignment(button_loseHome, Pos.CENTER);
        pane.setBottom(button_loseHome);

        // Background Image
        BackgroundImage bgImage = new BackgroundImage(new Image("Backgrounds/bg_lose.png"),
                null, null,
                null, null);
        Background bg = new Background(bgImage);
        pane.setBackground(bg);

        return new Scene(pane, 500, 800);
    }
}