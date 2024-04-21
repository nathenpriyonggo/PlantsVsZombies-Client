import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.HashSet;

public class PlacementPage {
    private Scene scene;
    private Stage stage;
    private Button[][] buttons = new Button[7][7];
    private boolean[][] shipPlaced = new boolean[7][7];
    private HashSet<String> shipsPlaced = new HashSet<>();
    private int currentShipSize = 2;  // Default to the smallest ship size
    private String currentShipName = "2 Pea Shooters";  // Default ship name
    private Button startButton = null;  // Start point of the ship placement
    private Button endButton = null;    // End point of the ship placement


    public PlacementPage(Stage stage) {
        this.stage = stage;
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #90EE90;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                Button gridButton = new Button();
                gridButton.setPrefSize(40, 40);
                gridButton.setStyle("-fx-border-color: black; " +
                        "-fx-border-width: 1; " +
                        "-fx-background-color: #FFFFFF;");
                gridButton.setOnAction(e -> {
                    if (startButton == null) {
                        startButton = gridButton;
                        startButton.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-background-color: #FFFFFF;");
                    } else if (endButton == null && gridButton != startButton) {
                        endButton = gridButton;
                        placeShip();
                    }
                });
                grid.add(gridButton, col, row);
                buttons[row][col] = gridButton;
            }
        }


        Text instructionText = new Text("Click on the grid to place your ships!");
        instructionText.setFont(new Font(16));
        StackPane header = new StackPane(instructionText);
        header.setPadding(new Insets(20, 0, 20, 0));

        BorderPane gridLayout = new BorderPane();
        gridLayout.setCenter(grid);
        gridLayout.setTop(header);

        VBox plantSelectionButtons = new VBox(10);
        plantSelectionButtons.setAlignment(Pos.CENTER);
        plantSelectionButtons.getChildren().addAll(
                createPlantButton("2 Pea Shooters", Color.GREEN, Color.WHITE),
                createPlantButton("3 Sunflowers", Color.GOLD, Color.BLACK),
                createPlantButton("3 Potato Mines", Color.SADDLEBROWN, Color.WHITE),
                createPlantButton("4 Snow Peas", Color.LIGHTBLUE, Color.BLACK),
                createPlantButton("5 Chompers", Color.PURPLE, Color.WHITE),
                createStartGameButton()
        );


        layout.setCenter(gridLayout);
        layout.setBottom(plantSelectionButtons);

        scene = new Scene(layout, 500, 800);
    }

    private Button createPlantButton(String text, Color bgColor, Color textColor) {
        Button button = new Button(text);
        button.setFont(new Font(12));
        button.setStyle(String.format("-fx-background-color: #%02X%02X%02X; -fx-text-fill: #%02X%02X%02X;",
                (int) (bgColor.getRed() * 255), (int) (bgColor.getGreen() * 255), (int) (bgColor.getBlue() * 255),
                (int) (textColor.getRed() * 255), (int) (textColor.getGreen() * 255), (int) (textColor.getBlue() * 255)));
        button.setPrefSize(150, 40);
        button.setOnAction(e -> {
            currentShipSize = Integer.parseInt(text.substring(0, 1));
            currentShipName = text;
        });
        return button;
    }

    private Button createStartGameButton() {
        Button button = new Button("Start Game");
        button.setFont(new Font(12));
        button.setStyle("-fx-background-color: #3EB489; -fx-text-fill: #FFFFFF;");
        button.setPrefSize(100, 40);
        // Start game button logic would go here
        return button;
    }

    private void placeShip() {
        if (shipsPlaced.contains(currentShipName)) {
            showAlert("This ship has already been placed. Please choose another.");
            // Reset styles for the next use
            startButton.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #FFFFFF;");
            endButton.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #FFFFFF;");
            resetSelection();
            return;
        }

        int startRow = GridPane.getRowIndex(startButton);
        int startCol = GridPane.getColumnIndex(startButton);
        int endRow = GridPane.getRowIndex(endButton);
        int endCol = GridPane.getColumnIndex(endButton);

        // Reset styles for the next use
        startButton.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #FFFFFF;");
        endButton.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #FFFFFF;");

        // Determine orientation and check if valid
        boolean isVertical = startCol == endCol;
        boolean isHorizontal = startRow == endRow;
        int size = currentShipSize - 1;

        if (!(isVertical || isHorizontal) || (isVertical && Math.abs(endRow - startRow) != size) || (isHorizontal && Math.abs(endCol - startCol) != size)) {
            showAlert("Invalid placement. Only place your grids next to each other horizontally or vertically.");
            resetSelection();
            return;
        }

        // Check for any existing ships in the placement area and place the ship
        if (isVertical) {
            for (int row = Math.min(startRow, endRow); row <= Math.max(startRow, endRow); row++) {
                if (shipPlaced[row][startCol]) {
                    showAlert("Invalid placement. There is already another ship in the way.");
                    resetSelection();
                    return;
                }
            }
            for (int row = Math.min(startRow, endRow); row <= Math.max(startRow, endRow); row++) {
                buttons[row][startCol].setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: " + getColorForCurrentShip() + ";");
                buttons[row][startCol].setDisable(true);
                shipPlaced[row][startCol] = true;
            }
        } else {
            for (int col = Math.min(startCol, endCol); col <= Math.max(startCol, endCol); col++) {
                if (shipPlaced[startRow][col]) {
                    showAlert("Invalid placement. There is already another ship in the way.");
                    resetSelection();
                    return;
                }
            }
            for (int col = Math.min(startCol, endCol); col <= Math.max(startCol, endCol); col++) {
                buttons[startRow][col].setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: " + getColorForCurrentShip() + ";");
                buttons[startRow][col].setDisable(true);
                shipPlaced[startRow][col] = true;
            }
        }
        shipsPlaced.add(currentShipName);
        resetSelection();
        // Automatically move to the next ship size or indicate placement is done
        if (currentShipSize < 5) {
            currentShipSize++;
        } else {
            showAlert("All ships placed. Starting the game!");
        }
    }
    private void resetSelection() {
        // Reset values
        startButton = null;
        endButton = null;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getColorForCurrentShip() {
        switch (currentShipName) {
            case "2 Pea Shooters": return "#90EE90";  // Light green
            case "3 Sunflowers": return "#FFD700";  // Gold
            case "3 Potato Mines": return "#8B4513";  // Saddle Brown
            case "4 Snow Peas": return "#ADD8E6";  // Light Blue
            case "5 Chompers": return "#800080";  // Purple
            default: return "#FFFFFF";  // White, should not happen
        }
    }

    public Scene getScene() {
        return scene;
    }
}
