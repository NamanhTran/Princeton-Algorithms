import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private char[] gameBoard;
    private int dimension;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        gameBoard = new char[dimension * dimension];
        int arrIndex = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                gameBoard[arrIndex++] = (char) tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append(dimension);
        boardString.append('\n');

        for (int i = 0; i < gameBoard.length; i += dimension) {
            for (int j = i; j < i + dimension; j++) {
                if ((int) gameBoard[toOneDim(i, j)] < 10)
                    boardString.append(" ");

                boardString.append((int) gameBoard[toOneDim(i, j)]);
                boardString.append(" ");
            }

            boardString.append('\n');
        }

        return boardString.toString();
    }


    // board dimension n
    public int dimension() {
        // Returns the amount of rows which is the same as the amount of columns
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDistance = 0;

        for (int i = 0; i < gameBoard.length; i += dimension) {
            for (int j = i; j < i + dimension; j++) {
                int tile = (int) gameBoard[toOneDim(i, j)];
                int correctTileValue = (dimension * (i / dimension)) + (j % dimension) + 1;

                if (tile != 0 && tile != correctTileValue)
                    hammingDistance++;
            }
        }

        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal (WORKING ON CONVERTING FROM 2D TO 1D array)
    public int manhattan() {
        int boardSize = dimension();
        int manhattanDistance = 0;

        for (int i = 0; i < gameBoard.length; i += dimension) {
            for (int j = i; j < i + dimension; j++) {
                int curRow = i / dimension;
                int curCol = j % dimension;
                int tile = (int) gameBoard[toOneDim(i, j)];
                int correctTileValue = (dimension * (i / dimension)) + (j % dimension) + 1;

                if (tile != 0 && tile != correctTileValue) {
                    double correctRow = Math.ceil(tile / (boardSize * 1.0)) - 1;
                    int correctColumn = (tile % boardSize) - 1;
                    if (correctColumn == -1)
                        correctColumn = dimension - 1;

                    manhattanDistance += Math.abs(correctRow - curRow) + Math
                            .abs(correctColumn - curCol);
                }
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (hamming() == 0)
            return true;

        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }

        return Arrays.equals(this.gameBoard, ((Board) y).gameBoard);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int emptySpaceRow = 0;
        int emptySpaceCol = 0;

        // Find the empty space
        for (int i = 0; i < gameBoard.length; i += dimension) {
            for (int j = i; j < i + dimension; j++) {
                if ((int) gameBoard[toOneDim(i, j)] == 0) {
                    emptySpaceRow = i / dimension;
                    emptySpaceCol = j % dimension;
                }
            }
        }

        // Get the top
        if (emptySpaceRow - 1 >= 0) {
            // Make a copy of the board
            int[][] boardCpy = cpyTo2DArr();

            // Swap blank with bottom tile
            int swap = boardCpy[emptySpaceRow - 1][emptySpaceCol];
            boardCpy[emptySpaceRow - 1][emptySpaceCol] = 0;
            boardCpy[emptySpaceRow][emptySpaceCol] = swap;


            // Add to the List
            neighbors.add(new Board(boardCpy));
        }

        // Get the bottom
        if (emptySpaceRow + 1 < dimension()) {
            // Make a copy of the board
            int[][] boardCpy = cpyTo2DArr();

            // Swap blank with bottom tile
            int swap = boardCpy[emptySpaceRow + 1][emptySpaceCol];
            boardCpy[emptySpaceRow + 1][emptySpaceCol] = 0;
            boardCpy[emptySpaceRow][emptySpaceCol] = swap;


            // Add to the List
            neighbors.add(new Board(boardCpy));
        }

        // Get the left
        if (emptySpaceCol - 1 >= 0) {
            // Make a copy of the board
            int[][] boardCpy = cpyTo2DArr();

            // Swap blank with bottom tile
            int swap = boardCpy[emptySpaceRow][emptySpaceCol - 1];
            boardCpy[emptySpaceRow][emptySpaceCol - 1] = 0;
            boardCpy[emptySpaceRow][emptySpaceCol] = swap;

            // Add to the List
            neighbors.add(new Board(boardCpy));
        }

        // Get the right
        if (emptySpaceCol + 1 < dimension()) {
            // Make a copy of the board
            int[][] boardCpy = cpyTo2DArr();

            // Swap blank with bottom tile
            int swap = boardCpy[emptySpaceRow][emptySpaceCol + 1];
            boardCpy[emptySpaceRow][emptySpaceCol + 1] = 0;
            boardCpy[emptySpaceRow][emptySpaceCol] = swap;

            // Add to the List
            neighbors.add(new Board(boardCpy));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return (this);
    }

    private int toOneDim(int row, int col) {
        int index = row + (col % dimension);

        return index;
    }

    private int[][] cpyTo2DArr() {
        int[][] boardCpy = new int[dimension()][dimension()];
        for (int i = 0; i < gameBoard.length; i += dimension) {
            for (int j = i; j < i + dimension; j++) {
                boardCpy[i / dimension][j % dimension] = gameBoard[toOneDim(i, j)];
            }
        }

        return boardCpy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board test = new Board(tiles);
        System.out.println(test.toString());
        System.out.println("Hamming: " + test.hamming());
        System.out.println("Manhattan: " + test.manhattan());
        for (Board neighbor : test.neighbors()) {
            System.out.println(neighbor.toString());
        }
    }

}
