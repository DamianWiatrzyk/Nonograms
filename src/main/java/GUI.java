//import java.util.ArrayList;
//
//import org.chocosolver.solver.variables.BoolVar;
//
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.Paint;
//import javafx.scene.Scene;
//import javafx.scene.layout.Priority;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//public class GUI extends Application {
//    private static final int TILE_SIZE = 50;
//    private static final int NUM_OF_ROWS = 15;
//    private static final int NUM_OF_COLS = 15;
//    private static final int GAME_SQUARE_SIZE = NUM_OF_ROWS * TILE_SIZE;
//
//    private Pane createPuzzle() {
//        Pane puzzleBoard = new Pane();
//        puzzleBoard.setPrefSize(GAME_SQUARE_SIZE, GAME_SQUARE_SIZE);
//
//        ArrayList<Tile> tiles = new ArrayList<>();
//
//        for (int i = 0; i < NUM_OF_ROWS; i++) {
//            for (int j = 0; j < NUM_OF_COLS; j++)
//                tiles.add(new Tile());
//        }
//
//        for (int i = 0; i < tiles.size(); i++) {
//            Tile tile = tiles.get(i);
//            tile.setTranslateX(TILE_SIZE * (i % NUM_OF_ROWS));
//            tile.setTranslateY(TILE_SIZE * (i / NUM_OF_ROWS));
//            puzzleBoard.getChildren().add(tile);
//        }
//
//        return puzzleBoard;
//    }
//
//    private void drawPuzzle(Pane puzzleBoard, BoolVar[][] cells) {
//        puzzleBoard.getChildren().clear();
//
//        ArrayList<Tile> tiles = new ArrayList<>();
//
//        for (int i = 0; i < NUM_OF_ROWS; i++) {
//            for (int j = 0; j < NUM_OF_COLS; j++)
//                tiles.add(new Tile(cells[i][j].getValue() == 1 ? Color.BLACK : Color.WHITE));
//        }
//
//        for (int i = 0; i < tiles.size(); i++) {
//            Tile tile = tiles.get(i);
//            tile.setTranslateX(TILE_SIZE * (i % NUM_OF_ROWS));
//            tile.setTranslateY(TILE_SIZE * (i / NUM_OF_ROWS));
//            puzzleBoard.getChildren().add(tile);
//        }
//    }
//
//    private class Tile extends StackPane {
//        private Text text = new Text();
//
//        public Tile(Paint color) {
//            Rectangle border = new Rectangle(TILE_SIZE, TILE_SIZE);
//            border.setFill(color);
//            border.setStroke(Color.BLACK);
//
//            text.setText("");
//            text.setFont(Font.font(30));
//
//            setAlignment(Pos.CENTER);
//            getChildren().addAll(border, text);
//        }
//
//        public Tile() {
//            this(Color.WHITE);
//        }
//
//        public void setText(String value) {
//        	text.setText(value);
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Puzzles puzzles = new Puzzles();
//
//        Pane puzzleBoard = createPuzzle();
//
//        Button solveBtn = new Button("Solve Nonogram");
//        Button exitBtn = new Button("Exit");
//        ComboBox puzzleSelect = new ComboBox();
//
//        solveBtn.setMaxWidth(Double.MAX_VALUE);
//        solveBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                Nonogram temp = new Nonogram();
//
//                switch((String)puzzleSelect.getValue()){
//                    case "Bread":
//                        temp.modelAndSolve(puzzles.getPuzzle_Bread());
//                        break;
//                    case "Butterfly":
//                        temp.modelAndSolve(puzzles.getPuzzle_Butterfly());
//                        break;
//                    case "Castle":
//                        temp.modelAndSolve(puzzles.getPuzzle_Castle());
//                        break;
//                    case "Pegasus":
//                        temp.modelAndSolve(puzzles.getPuzzle_Pegasus());
//                        break;
//                }
//                drawPuzzle(puzzleBoard, temp.getCells());
//            }
//        });
//
//        exitBtn.setMaxWidth(Double.MAX_VALUE);
//        exitBtn.setOnAction(e -> Platform.exit());
//
//        puzzleSelect.setMaxWidth(Double.MAX_VALUE);
//        puzzleSelect.getItems().addAll(
//            "Bread",
//            "Butterfly",
//            "Castle",
//            "Pegasus"
//        );
//        puzzleSelect.getSelectionModel().selectFirst();
//
//        VBox controls = new VBox(10);
//        Region spacer = new Region();
//
//        VBox.setVgrow(spacer, Priority.ALWAYS);
//        controls.getChildren().addAll(solveBtn, puzzleSelect, spacer, exitBtn);
//
//        HBox root = new HBox(10);
//        root.setStyle("-fx-padding: 10;");
//        root.getChildren().addAll(puzzleBoard, controls);
//
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setResizable(false);
//        primaryStage.setTitle("Nonogram Solver");
//        primaryStage.show();
//    }
//}
