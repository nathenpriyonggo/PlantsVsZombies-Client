import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RulesPage {
    private Scene scene;
    private Stage stage;

    public RulesPage(Stage stage) {
        this.stage = stage;
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Rules title and text
        Text rulesTitle = new Text("Game Rules");
        rulesTitle.setFont(new Font(20));
        layout.getChildren().add(rulesTitle);

        // Example game rules
        String[] rules = {
                "1. Each plant occupies specific grid spaces.",
                "2. Plants cannot overlap each other on the grid.",
                "3. Position your plants to block zombies effectively.",
                "4. Plants can only be placed horizontally or vertically.",
                "5. The game ends when all plants are placed or zombies reach your house.",
                "6. Points are scored for every zombie stopped.",
                "7. Use Sunflowers to gain extra points."
        };

        for (String rule : rules) {
            Text ruleText = new Text(rule);
            ruleText.setFont(new Font(14));
            layout.getChildren().add(ruleText);
        }

        // Back button positioned at the top left
        Button btnBack = new Button("Back");
        btnBack.setFont(new Font(14));
        btnBack.setStyle("-fx-background-color:#15A3C7; -fx-text-fill: black;"); // Style for the button
        btnBack.setOnAction(e -> stage.setScene(new HomePage(stage).getScene()));

        // Create a BorderPane as the main layout
        BorderPane root = new BorderPane();
        root.setCenter(layout);
        root.setTop(btnBack);
        BorderPane.setAlignment(btnBack, Pos.TOP_LEFT);
        BorderPane.setMargin(btnBack, new Insets(10));

        // Set the entire background to light blue
        root.setStyle("-fx-background-color: #ADD8E6;");

        // Finalize the scene
        scene = new Scene(root, 500, 800);
    }

    public Scene getScene() {
        return scene;
    }
}
