public class Main {

    public static void main(String[] args) {

        Nonogram temp = new Nonogram();
        temp.modelAndSolve("puzzles/butterfly.txt");
        if(temp.isSolved()){
            temp.printCells();
        }
    }
}
