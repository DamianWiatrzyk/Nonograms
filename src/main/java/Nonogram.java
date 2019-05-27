import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.constraints.nary.automata.FA.IAutomaton;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.util.tools.ArrayUtils;

public class Nonogram {
    // number of columns
    private int N = 15;
    // number of rows
    private int M = 15;
    private Model model = new Model("Nonogram");
    // Variables declaration
    private BoolVar[][] cells = model.boolVarMatrix("c", N, M);

    private boolean isSolved = false;

    public void modelAndSolve(int[][][] Puzzle) {
        for (int i = 0; i < M; i++) {
            dfa(cells[i], Puzzle[0][i], model);
        }
        for (int j = 0; j < N; j++) {
            dfa(ArrayUtils.getColumn(cells, j), Puzzle[1][j], model);
        }
        if(model.getSolver().solve()){
            this.isSolved=true;
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

    public void printCells(){
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
}