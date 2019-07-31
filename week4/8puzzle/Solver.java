import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private Iterable<Board> solutionPath;
    private int totalMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        BoardData root = new BoardData(initial, null, 0);
        MinPQ<BoardData> solverHeap = new MinPQ<BoardData>(root.piorityOrder());
        solverHeap.insert(root);
        BoardData minBoard;
        int num = 0;

        do {

            // System.out.println("Step " + num);
            //
            // for (BoardData items : solverHeap) {
            //     System.out.println("Piority: " + items.getPiority() + "\nMoves: " + items.getMoves()
            //                                + "\nManhattan: " + items.getManhattan());
            //     System.out.println(items.getBoard().toString());
            // }


            minBoard = solverHeap.delMin();

            Iterable<Board> neighbors = minBoard.getBoard().neighbors();
            for (Board neighbor : neighbors) {

                // If min node doesn't have parent
                if (minBoard.getParent() == null)
                    solverHeap.insert(new BoardData(neighbor, minBoard, minBoard.getMoves() + 1));

                    // Need to avoid neighbor that is the same as the grandparent (min's parent)
                else if (!neighbor.equals(minBoard.getParent().getBoard()))
                    solverHeap.insert(new BoardData(neighbor, minBoard, minBoard.getMoves() + 1));
            }

            num++;

        } while (minBoard.getManhattan() != 0);

        totalMoves = minBoard.getMoves();

        // Get the solution path
        LinkedList<Board> solution = new LinkedList<Board>();
        while (minBoard != null) {
            solution.addFirst(minBoard.getBoard());
            minBoard = minBoard.getParent();
        }

        solutionPath = solution;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board
    public int moves() {
        return totalMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solutionPath;
    }

    private class BoardData {
        private Board board;
        private int moves;
        private int manhattan;
        private int piority;
        private BoardData parent;

        public BoardData(Board gameBoard, BoardData parent, int moves) {
            board = gameBoard;
            this.moves = moves;
            manhattan = gameBoard.manhattan();
            piority = moves + manhattan;
            this.parent = parent;
        }

        public int getMoves() {
            return moves;
        }

        public int getManhattan() {
            return manhattan;
        }

        public int getPiority() {
            return piority;
        }

        public Board getBoard() {
            return board;
        }

        public BoardData getParent() {
            return parent;
        }

        public Comparator<BoardData> piorityOrder() {
            return (boardData1, boardData2) -> {
                if (boardData1.getPiority() > boardData2.getPiority())
                    return 1;

                else if (boardData1.getPiority() < boardData2.getPiority())
                    return -1;

                else {
                    if (boardData1.getManhattan() > boardData1.getManhattan())
                        return 1;

                    else if (boardData1.getManhattan() < boardData2.getManhattan())
                        return -1;

                    else
                        return 0;
                }
            };
        }
    }

    // test client
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
