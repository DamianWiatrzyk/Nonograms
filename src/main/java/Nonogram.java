import com.google.gson.Gson;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.constraints.nary.automata.FA.IAutomaton;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.util.tools.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Nonogram {

    private Model model = new Model("Nonogram");
    
    private BoolVar[][] cells;

    private boolean isSolved = false;
    
    private int N;
    private int M;
    private int[][] puzzleY;
    private int[][] puzzleX;


    public void modelAndSolve(String path) {
        Gson gson = new Gson();
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Puzzle pz = gson.fromJson(text, Puzzle.class);
        N = pz.getN();
        M = pz.getM();
        puzzleY = pz.getPuzzleY();
        puzzleX = pz.getPuzzleX();
        this.cells = model.boolVarMatrix("c", N, M);
        for (int i = 0; i < M; i++) {
            dfa(cells[i], puzzleY[i], model);
        }
        for (int j = 0; j < N; j++) {
            dfa(ArrayUtils.getColumn(cells, j), puzzleX[j], model);
        }
        if (model.getSolver().solve()) {
            this.isSolved = true;
        }
    }

    private void dfa(BoolVar[] cells, int[] seq, Model model) {
        StringBuilder regexp = new StringBuilder("0*");
        int m = seq.length;
        for (int i = 0; i < m; i++) {
            regexp.append('1').append('{').append(seq[i]).append('}');
            regexp.append('0');
            regexp.append(i == m - 1 ? '*' : '+');
        }
        IAutomaton auto = new FiniteAutomaton(regexp.toString());
        model.regular(cells, auto).post();
    }

    public BoolVar[][] getCells() {
        return cells;
    }
    
    public int getWidth() {
        return M;
    }
    
    public int getHeight() {
        return N;
    }   
    
    public int[][] getRows() {
        return puzzleY;
    }
    
    public int[][] getColumns() {
        return puzzleX;
    }       
    public void printCells() {
        for (int i = 0; i < cells.length; i++) {
            System.out.printf("\t");
            for (int j = 0; j < cells[i].length; j++) {
                System.out.printf(cells[i][j].getValue() == 1 ? "#" : ".");
            }
            System.out.printf("\n");
        }
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void loadPuzzleFromJSON(String path){
        Gson gson = new Gson();
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Puzzle pz = gson.fromJson(text, Puzzle.class);

    }

}