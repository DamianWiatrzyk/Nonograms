import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import java.io.File;
import java.nio.file.Paths;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class GUI extends Application {
    private static final int GAME_SQUARE_SIZE = 900;
    private static int gameSize = 0;
    private static int labelSize = 0;
    private static int tileSize = 0;
//    private static final int NUM_OF_ROWS = 20;
//    private static final int NUM_OF_COLS = 20;
//    private static final int LABEL_SIZE = 5;
//    private static final int TILE_SIZE = GAME_SQUARE_SIZE / (NUM_OF_ROWS + LABEL_SIZE);

    private Pane createPuzzle() {
        Pane puzzleBoard = new Pane();
        puzzleBoard.setPrefSize(GAME_SQUARE_SIZE, GAME_SQUARE_SIZE);

        TextTile tile = new TextTile(GAME_SQUARE_SIZE);
        puzzleBoard.getChildren().add(tile);

        return puzzleBoard;
    }
    
    private int getMaxLabel(Nonogram puzzle){
        int maxLabel = 0;
        
        int[][] puzzleXY = puzzle.getColumns();
        for (int i = 0; i < puzzleXY.length; i++)
            if(puzzleXY[i].length > maxLabel)
                maxLabel = puzzleXY[i].length;
        
        puzzleXY = puzzle.getRows();
        for (int i = 0; i < puzzleXY.length; i++)
            if(puzzleXY[i].length > maxLabel)
                maxLabel = puzzleXY[i].length;
        return maxLabel;
    }

    private void drawPuzzle(Pane puzzleBoard, Nonogram puzzle) {
        puzzleBoard.getChildren().clear();
        
        gameSize = puzzle.getHeight();
        labelSize = getMaxLabel(puzzle);
        tileSize = GAME_SQUARE_SIZE / (gameSize + labelSize);
        
        TextTile zeroTile = new TextTile(labelSize * tileSize);
        puzzleBoard.getChildren().add(zeroTile);
        
        ArrayList<TextTile> rows = new ArrayList<>();

        for (int i = 0; i < labelSize * gameSize; i++) {
            rows.add(new TextTile());
        }

        for (int i = 0; i < rows.size(); i++) {
            TextTile tile = rows.get(i);
            tile.setTranslateX(tileSize * (i % labelSize));
            tile.setTranslateY(tileSize * ((i / labelSize) + labelSize));
            puzzleBoard.getChildren().add(tile);
        }
        
        {
            int[][] puzzleY = puzzle.getRows();
            for (int i = 0; i < puzzleY.length; i++){
                int k = (i + 1) * labelSize - puzzleY[i].length;
                for (int j = 0; j < puzzleY[i].length; j++){
                    TextTile tile = rows.get(k);
                    tile.setText(Integer.toString(puzzleY[i][j]));
                    rows.set(i, tile);
                    k++;
                }
            }          
        }
        
        ArrayList<TextTile> columns = new ArrayList<>();

        for (int i = 0; i < labelSize * gameSize; i++) {
            columns.add(new TextTile());
        }

        for (int i = 0; i < columns.size(); i++) {
            TextTile tile = columns.get(i);
            tile.setTranslateX(tileSize * ((i / labelSize) + labelSize));
            tile.setTranslateY(tileSize * (i % labelSize));
            puzzleBoard.getChildren().add(tile);
        }
        
        {
            int[][] puzzleX = puzzle.getColumns();
            for (int i = 0; i < puzzleX.length; i++){
                int k = (i + 1) * labelSize - puzzleX[i].length;
                for (int j = 0; j < puzzleX[i].length; j++){
                    TextTile tile = columns.get(k);
                    tile.setText(Integer.toString(puzzleX[i][j]));
                    columns.set(i, tile);
                    k++;
                }
            }          
        }

        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++)
                tiles.add(new Tile(puzzle.getCells()[i][j].getValue() == 1 ? Color.BLACK : Color.WHITE));
        }
        
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setTranslateX(tileSize * ((i % gameSize) + labelSize));
            tile.setTranslateY(tileSize * ((i / gameSize) + labelSize));
            puzzleBoard.getChildren().add(tile);
        }    
    }

    private class Tile extends StackPane {

        public Tile(Paint color) {
            Rectangle border = new Rectangle(tileSize, tileSize);
            border.setFill(color);
            border.setStroke(Color.BLACK);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border);
        }

        public Tile() {
            this(Color.WHITE);
        }
    }
    
    private class TextTile extends StackPane {
        private Text text = new Text();

        public TextTile(int size) {
            Rectangle border = new Rectangle(size, size);
            border.setFill(Color.WHITE);
            border.setStroke(Color.WHITE);

            text.setText("");
            text.setFont(Font.font((3.0/5) * tileSize));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
        
        public TextTile(String text){
            this(tileSize);
            this.text.setText(text);
        }
        
        public TextTile(){
            this(tileSize);;
        }

        public void setText(String value) {
        	text.setText(value);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public class PuzzleItem {
        private String name;
        private String path;

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }

        public PuzzleItem(String name, String path) {
            this.name = name;
            this.path = path;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox(10);
        VBox controls = new VBox(10);
        Pane puzzleBoard = createPuzzle();
        
        FileChooser fileChooser = new FileChooser();
        String currentPath = Paths.get("./puzzles").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        Button loadBtn = new Button("Load Nonogram");
        Button solveBtn = new Button("Solve Nonogram");
        Button exitBtn = new Button("Exit");
        ComboBox<PuzzleItem> puzzleSelect = new ComboBox<PuzzleItem>();

        solveBtn.setMaxWidth(Double.MAX_VALUE);
        solveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Nonogram temp = new Nonogram();

                temp.modelAndSolve(puzzleSelect.getValue().getPath());
                drawPuzzle(puzzleBoard, temp);
//                root.getChildren().remove(puzzleBoard);
                root.autosize();
                primaryStage.sizeToScene();
            }
        });

        loadBtn.setMaxWidth(Double.MAX_VALUE);
        loadBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                File file = fileChooser.showOpenDialog(primaryStage);
                String fileName = file.getName();
                fileName = fileName.substring(0, fileName.lastIndexOf('.')).toLowerCase();
                fileName = Character.toUpperCase(fileName.charAt(0)) + fileName.substring(1);
                PuzzleItem item = new PuzzleItem(fileName, file.getAbsolutePath());
                puzzleSelect.getItems().add(item);
                puzzleSelect.setValue(item);
            }
        });
        
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn.setOnAction(e -> Platform.exit());

        puzzleSelect.setMaxWidth(Double.MAX_VALUE);
        puzzleSelect.setVisibleRowCount(6);
        puzzleSelect.getSelectionModel().selectFirst();
        
        puzzleSelect.setConverter(new StringConverter<PuzzleItem>() {
            @Override
            public String toString(PuzzleItem object) {
                return object.getName();
            }

            @Override
            public PuzzleItem fromString(String string) {
                return null;
            }
        });

        Region spacer = new Region();

        VBox.setVgrow(spacer, Priority.ALWAYS);
        controls.getChildren().addAll(solveBtn, loadBtn, puzzleSelect, spacer, exitBtn);

        root.setStyle("-fx-padding: 10;");
        root.getChildren().addAll(puzzleBoard, controls);

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Nonogram Solver");
        primaryStage.show();
    }
}
