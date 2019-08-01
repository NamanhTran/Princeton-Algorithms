import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private Iterable<Board> solutionPath;
    private int totalMoves = -1;
    private boolean notSolvable = false;
    private ArrayList<Integer> cache = new ArrayList<Integer>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        BoardData root = new BoardData(initial, null, 0);
        MinPQ<BoardData> solverHeap = new MinPQ<BoardData>(piorityOrder());
        solverHeap.insert(root);

        BoardData rootTwin = new BoardData(initial.twin(), null, 0);
        MinPQ<BoardData> solverHeapTwin = new MinPQ<BoardData>(piorityOrder());
        solverHeapTwin.insert(rootTwin);

        BoardData minBoard;
        BoardData minBoardTwin;
        int num = 0;

        do {

            // System.out.println("Step " + num + "\n\n");
            //
            // for (BoardData items : solverHeap) {
            //     System.out.println("Piority: " + items.getPiority() + "\nMoves: " + items.getMoves()
            //                                + "\nManhattan: " + items.getManhattan());
            //     System.out.println(items.getBoard().toString());
            // }


            minBoard = solverHeap.delMin();

            Iterable<Board> neighbors = minBoard.board.neighbors();
            for (Board neighbor : neighbors) {

                // If min node doesn't have parent
                if (minBoard.parent == null)
                    solverHeap.insert(new BoardData(neighbor, minBoard, minBoard.moves + 1));

                    // Need to avoid neighbor that is the same as the grandparent (min's parent)
                else if (!neighbor.equals(minBoard.parent.board))
                    solverHeap.insert(new BoardData(neighbor, minBoard, minBoard.moves + 1));
            }

            // Twin Runthrough
            minBoardTwin = solverHeapTwin.delMin();

            Iterable<Board> neighborsTwin = minBoardTwin.board.neighbors();
            for (Board neighbor : neighborsTwin) {

                // If min node doesn't have parent
                if (minBoardTwin.parent == null)
                    solverHeapTwin.insert(new BoardData(neighbor, minBoardTwin,
                                                        minBoardTwin.moves + 1));

                    // Need to avoid neighbor that is the same as the grandparent (min's parent)
                else if (!neighbor.equals(minBoardTwin.parent.board))
                    solverHeapTwin.insert(new BoardData(neighbor, minBoardTwin,
                                                        minBoardTwin.moves + 1));
            }

            num++;

        } while (!minBoard.board.isGoal() && !minBoardTwin.board.isGoal());

        if (minBoardTwin.board.isGoal()) {
            notSolvable = true;
            return;
        }

        totalMoves = minBoard.moves;

        // Get the solution path
        LinkedList<Board> solution = new LinkedList<Board>();
        while (minBoard != null) {
            solution.addFirst(minBoard.board);
            minBoard = minBoard.parent;
        }

        solutionPath = solution;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return notSolvable;
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
        public final Board board;
        public final int moves;
        public final int manhattan;
        public final int piority;
        public final BoardData parent;

        public BoardData(Board gameBoard, BoardData parent, int moves) {
            board = gameBoard;
            this.moves = moves;
            manhattan = gameBoard.manhattan();
            piority = moves + manhattan;
            this.parent = parent;
        }
    }

    public Comparator<BoardData> piorityOrder() {
        return (boardData1, boardData2) -> {
            if (boardData1.piority > boardData2.piority)
                return 1;

            else if (boardData1.piority < boardData2.piority)
                return -1;

            else {
                if (boardData1.manhattan > boardData2.manhattan)
                    return 1;

                else if (boardData1.manhattan < boardData2.manhattan)
                    return -1;

                else
                    return 0;
            }
        };
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
