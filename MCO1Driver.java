import java.util.*;

public class MCO1Driver {
    
  

  
    public static void main(String[] args) {
      Scanner s = new Scanner(System.in);
      Board b = new Board();
	    b.displayBoard();
      int win = 0;
      int playerTurn = 1;
      String pieceName;
      boolean didMove;
      Piece piece;
      // generate board
      // generate pieces
      // player 1 moves then 2 repeat
      // is valid move to check
      //if not lake and not occupied move to square
      //if lake and piece can cross lake then call function crosslake crosslake will check 
      //next sqaure if its still lake till its not a lake then move it there unless there a rat on a lake square in the middle
      //if lake but piece is rat move as normal
      // 1st player to make a piece get to enemy base wins
      
      while (win == 0) {
        didMove = true;
        while (didMove == false) { 
          System.out.println("Player" + playerTurn + "'s turn");
          System.out.println("Choose which piece to move by giving the displayed character: ");
          pieceName = s.nextLine();
          
          // move piece method here
          //didMove = [method here];
          
  
          if (didMove == true){
            playerTurn = 2;
          }
          else if (didMove == false){
            System.out.println("Error moving chosen piece try again");
          }
        }


      }

      
      
    }

}
