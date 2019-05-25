public class Main {

    public static void main(String[] args) {
        Puzzles puzzles = new Puzzles();
        Nonogram temp = new Nonogram();
        temp.modelAndSolve(puzzles.getPuzzle_Butterfly());
        if(temp.isSolved()){
            temp.printCells();
        }
    }
}
