package problems.ConnectFour;

enum GameState{
    WON,
    DRAW,
    IN_PROGRESS
}

public class Game{
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private GameState state; //enum
    private Player winner; // can be null

    public Game(Player player1, Player player2){
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.state = GameState.IN_PROGRESS;
        this.winner = null;
    }

    // Methods
    /*
    + Game(Player1, Player2)
    + makeMove(player, column) -> bool
    + getCurrentPlayer() -> Player
    + getGameState() -> GameState
    + getWinner() -> Player
    + getBoard() -> Board
     */

    public boolean makeMove(Player player, int column){
        if (state != GameState.IN_PROGRESS) {
            return false;
        }

        if (player != currentPlayer){
            return false;
        }

        int row = board.placeDisc(column, player.getColor());
        if(row == -1){
            return false;
        }

        if (board.checkWin(row, column, player.getColor()) ){
            state = GameState.WON;
            winner = player;
        }else if(board.isFull()){
            state = GameState.DRAW;
        }else{
            currentPlayer = (player == player1) ? player2 : player1; // switch turns
        }
        return true;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public GameState getGameState() {
        return state;
    }

    public Player getWinner() {
        return winner;
    }

    public Board getBoard() {
        return board;
    }
}