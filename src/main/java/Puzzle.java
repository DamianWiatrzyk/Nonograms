public class Puzzle {
    int N;
    int M;
    int[][] puzzleY;
    int[][] puzzleX;

    public Puzzle(int N, int M, int[][] puzzleY, int[][] puzzleX){
        this.N=N;
        this.M = M;
        this.puzzleY = puzzleY;
        this.puzzleX = puzzleX;
    }

    public int[][] getPuzzleX() {
        return puzzleX;
    }

    public int[][] getPuzzleY() {
        return puzzleY;
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }
}


