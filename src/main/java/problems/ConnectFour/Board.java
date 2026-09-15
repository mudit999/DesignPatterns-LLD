package problems.ConnectFour;

enum DiscColor{
    RED,
    YELLOW
}

public class Board {
    private final int ROWS = 6;
    private final int COLS = 7;
    private final DiscColor[][] grid;

    public Board() {
        this.grid = new DiscColor[ROWS][COLS];
    }

    /*
    + Board
    + getRows() -> int
    + getCols() -> int
    + canPlace(column) -> bool
    + placeDisc(column, color) -> row
    + isFull() -> bool
    + checkWin(row, column, color) -> bool
    + getCell(row, col) -> DiscColor
     */

    public int getRows() {
        return ROWS;
    }

    public int getCOLS() {
        return COLS;
    }

    public boolean canPlace(int column) {
        if (column < 0 || column > COLS) {
            return false; // out of bound
        }
        return grid[0][column] == null; // if whole column is filled, check at top
    }

    public int placeDisc(int column, DiscColor color) {

        if (!canPlace(column)) {
            return -1;
        }

        for (int i = ROWS - 1; i >= 0; i--) {
            if (grid[i][column] == null) {
                grid[i][column] = color;
                return i;
            }
        }

        return -1;
    }

    public boolean checkWin(int row, int col, DiscColor color) {
        if (!inBounds(row, col) || grid[row][col] != color) {
            return false;
        }

        int[][] direction = new int[][]{
                {0, 1}, // horizontal
                {1, 0}, // vertical
                {1, 1}, // Diagonal top left to down right
                {-1, 1}, // Diagonal down left to top right
        };

        for (int[] dir : direction) {
            int count = 1;
            count += countInDirection(row, col, dir[0], dir[1], color);
            count += countInDirection(row, col, -dir[0], -dir[1], color);

            if (count >= 4) {
                return true;
            }
        }

        return false;
    }

    private int countInDirection(int row, int col, int dx, int dy, DiscColor color) {
        int count = 0;
        int r = row + dx;
        int c = col + dy;

        while(inBounds(r,c) && grid[r][c] == color){
            count++;
            r += dx;
            c += dy;
        }

        return count;
    }

    private boolean inBounds(int row, int col){
        if(row >=0 && row < ROWS && col >= 0 && col < COLS){
            return true;
        }
        return false;
    }

    public DiscColor getCell(int row, int col){
        if(!inBounds(row, col)){
            return null;
        }
        return grid[row][col];
    }

    public boolean isFull(){
        for(int c=0; c <= COLS-1; c++){
            if(grid[0][c] == null){ // checking top row
                return false;
            }
        }
        return true;
    }
}