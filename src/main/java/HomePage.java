import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage {
    private Scene scene;
    private Stage stage;

    public HomePage(Stage stage) {
        this.stage = stage;
        BorderPane layout = new BorderPane();

        // User info
        VBox userInfo = new VBox(5);
        Text playerName = new Text("Hayley");
        playerName.setFont(new Font(14));  // Reduced font size for "Hayley"
        Text score = new Text("0");
        score.setFont(new Font(12));  // Reduced font size for "0"
        userInfo.getChildren().addAll(playerName, score);
        userInfo.setPadding(new Insets(10));
        layout.setLeft(userInfo);  // Ensures it stays at the top left

        // Rules button
        Button btnRules = new Button("Rules");
        btnRules.setFont(new Font(14));  // Font size for rules button
        btnRules.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: black;");
        btnRules.setOnAction(e -> stage.setScene(new RulesPage(stage).getScene()));
        BorderPane.setAlignment(btnRules, Pos.TOP_RIGHT);
        layout.setRight(btnRules);
        BorderPane.setMargin(btnRules, new Insets(10));

        // Game title
        Text title = new Text("Plants Vs Zombies");
        title.setFont(new Font(30));
        title.setStyle("-fx-font-weight: bold; -fx-fill: Black;"); // Adjust the font style as needed
        title.setFill(Color.BLACK); // Use setFill for Text nodes
        StackPane titleContainer = new StackPane(title);
        titleContainer.setAlignment(Pos.TOP_CENTER);
        titleContainer.setPadding(new Insets(20, 0, 0, 0)); // Adjust for top padding
        layout.setTop(titleContainer);
        // Center: Game logo
        Image logo = new Image("https://static.wikia.nocookie.net/logopedia/images/0/01/Pvz_logo_stacked_rgb.png/revision/latest?cb=20120408101754");
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitHeight(200);  // Adjust as needed
        StackPane logoContainer = new StackPane(logoView);
        logoContainer.setAlignment(Pos.CENTER);
        layout.setCenter(logoContainer);
        BorderPane.setMargin(logoContainer, new Insets(20, 0, 0, 0));  // Adjust the margin as needed

        // Play buttons
        Button btnRandomPvP = new Button("Random PvP");
        btnRandomPvP.setStyle("-fx-background-color: #006400; -fx-text-fill: white;");
        btnRandomPvP.setOnAction(e -> stage.setScene(new PlacementPage(stage).getScene()));

        Button btnAI = new Button("AI");
        btnAI.setStyle("-fx-background-color: #006400; -fx-text-fill: white;");

        HBox playButtons = new HBox(20, btnRandomPvP, btnAI);  // Added spacing
        playButtons.setAlignment(Pos.CENTER);
        layout.setBottom(playButtons);
        BorderPane.setMargin(playButtons, new Insets(10));

        // Setting up the scene
        this.scene = new Scene(layout, 500, 800);
        layout.setStyle("-fx-background-color: #90EE90;"); // Light green background
    }

    public Scene getScene() {
        return this.scene;
    }
}
