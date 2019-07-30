import java.util.ArrayList;

public class Board {

    private int[][] gameBoard;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        gameBoard = tiles;
    }

    // string representation of this board
    public String toString() {
        int size = dimension();
        StringBuilder boardString = new StringBuilder();
        boardString.append(size);
        boardString.append('\n');

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameBoard[i][j] < 10)
                    boardString.append(" ");

                boardString.append(gameBoard[i][j]);
                boardString.append(" ");
            }
            boardString.append('\n');
        }

        return boardString.toString();
    }


    // board dimension n
    public int dimension() {
        // Returns the amount of rows which is the same as the amount of columns
        return gameBoard.length;
    }

    // number of tiles out of place
    public int hamming() {
        int boardSize = dimension();
        int hammingDistance = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int tile = gameBoard[i][j];
                int correctTileValue = (boardSize * i) + j + 1;

                if (tile != 0 && tile != correctTileValue)
                    hammingDistance++;
            }
        }

        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int boardSize = dimension();
        int manhattanDistance = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int curRow = i;
                int curCol = j;
                int tile = gameBoard[i][j];
                int correctTileValue = (boardSize * curRow) + curCol + 1;

                if (tile != 0 && tile != correctTileValue) {
                    double correctRow = Math.ceil(tile / (boardSize * 1.0)) - 1;
                    int correctColumn = (tile % boardSize) - 1;
                    if (correctColumn == -1)
                        correctColumn = 0;

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
        return this == y;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int emptySpaceRow = 0;
        int emptySpaceCol = 0;

        // Find the empty space
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (gameBoard[i][j] == 0) {
                    emptySpaceRow = i;
                    emptySpaceCol = j;
                }
            }
        }

        // Get the top
        if (emptySpaceRow - 1 >= 0) {
            // Make a copy of the board
            int[][] boardCpy = new int[dimension()][dimension()];
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++)
                    boardCpy[i][j] = gameBoard[i][j];
            }

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
            int[][] boardCpy = new int[dimension()][dimension()];
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++)
                    boardCpy[i][j] = gameBoard[i][j];
            }

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
            int[][] boardCpy = new int[dimension()][dimension()];
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++)
                    boardCpy[i][j] = gameBoard[i][j];
            }

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
            int[][] boardCpy = new int[dimension()][dimension()];
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++)
                    boardCpy[i][j] = gameBoard[i][j];
            }

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

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
