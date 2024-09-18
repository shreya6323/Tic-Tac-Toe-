package org.example;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.Math.pow;
import static java.lang.System.exit;

public class Game {
  public static final char X_SYMBOL = 'X';
  public static final int X_SYMBOL_VALUE = -1;
  public static final int O_SYMBOL_VALUE = -2;
  public static final char O_SYMBOL = 'O';
  private static final int easy_level = 1;
  private static final int hard_level = 2;
  public static final String human = "\uD83E\uDEC5 ";
  public static final String robot = "\uD83E\uDD16 ";
  private Board boardobj;
  private Player player;
  private Player cyborg;
  private Scanner sc;
  private int level;


  public Game() {
    this.boardobj = new Board();
    this.player = new Human();
    this.cyborg = new Cyborg();
    sc = new Scanner(System.in);
  }

  private void whoWin(char ch) {
    StringBuilder result = new StringBuilder();
    if (ch == player.getSymbol()) {
      result.append(human.repeat(25));
      result.append("\n");
      result.append("Hurray \uD83E\uDD73, %s you nailed it \uD83E\uDD29 !: ".formatted(player.getName()));
      println(result.toString());

    } else {
      result.append(robot.repeat(25));
      result.append("\n");
      result.append("Cyborg win ! Bring on your defense game \uD83D\uDCAA next time !");
      println(result.toString());
    }
  }

  private boolean println(String text) {
    System.out.println(text);
    return true;
  }

  private void setPlayerName() {
    String name;

    do {
      println("Enter your name ✏\uFE0F");
      name = this.sc.nextLine();

    } while (name.isEmpty() && println("Sry, Empty name not allowed \uD83E\uDD25 !"));
    player.setName(name);
  }

  private void setPlayerSymbol() {
    String ip;
    do {
      String result = "%s choose your playerSymbol from { X, O }: ".formatted(player.getName());
      System.out.println(result);
      ip = this.sc.next();
      char ipchar = ip.toUpperCase().charAt(0);
      if(ip.length() != 1)
      {
        System.out.println("Sry, Only one character is allowed \uD83E\uDD25 !");
        ip="";
      }
     else if (ipchar == 'Q') {
        println("Are u leaving me in th middle ?? ohk then \uD83D\uDC4B \uD83D\uDC4B");
        exit(0);
      }
     else if(ipchar != X_SYMBOL && ipchar!= O_SYMBOL){
        println("Sry, Cannot enter anything except 'X' or 'O' \uD83E\uDD25 !");
        ip="";
      }
     else {
       player.setSymbol(ipchar);
      }
      sc.nextLine();
    } while (ip.isEmpty());
  }

  protected void initialStartup() {

    manual();
    setPlayerName();
    postStartup();
  }

  private void postStartup() {
    setPlayerSymbol();
    cyborg.setSymbol((player.getSymbol() == X_SYMBOL) ? O_SYMBOL : X_SYMBOL);
    askBoardSize();
    askDifficltyLevel();
    boardobj.initializeBoard();
    boardobj.showBoard();
    startPlay();
  }

  private void askDifficltyLevel() {
    level = -1;
   do {
      try {
        String result = "%s enter your difficulty level, %d for easy & %d for hard : ".formatted(player.getName(),easy_level,hard_level);
        println(result);
        level = this.sc.nextInt();
        if (level != easy_level && level != hard_level) {
            level = -1;
            throw new OutOfRange(" \uD83D\uDE4F Plzz enter %d or %d !".formatted(easy_level,hard_level));
        }

      } catch (InputMismatchException e) {
        println(" \uD83D\uDE4F Plzz Enter only numbers !!");

      } catch (OutOfRange e) {
        println(e.getMessage());

      } finally {
        sc.nextLine();
      }

    } while (level == -1);

  }

  private void startPlay() {
    if (player.getSymbol() == X_SYMBOL) {
      while (true) {
        playerPlay();
        computerPlay();
      }

    } else {
      while (true) {
        computerPlay();
        playerPlay();
      }
    }
  }

  private int getPositionFromUser() {
    int position = -1;

    do {
      try {
        String result = "%s enter empty location for the next move from 1 to %d : ".formatted(player.getName(),maxLimit());
        System.out.println(result);
        position = this.sc.nextInt();
        if (!(position >= 1 && position <= maxLimit())) {
          position = -1;
          throw new OutOfRange(" \uD83D\uDE4F Plzz enter position between 1 to %d only !".formatted(maxLimit()));
        } else if (!boardobj.getEmptyPositions().contains(position)) {
          position = -1;
          throw new OutOfPosition(" \uD83D\uDE4F Plzz enter an empty position !");
        }

      } catch (InputMismatchException e) {
        println(" \uD83D\uDE4F Plzz Enter only numbers  from 1 to %d !!".formatted(maxLimit()));

      } catch (OutOfRange | OutOfPosition e) {
        println(e.getMessage());

      } finally {
        sc.nextLine();
      }

    } while (position == -1);
    return position;
  }

  private int getComputerMove() {
    Cyborg c = (Cyborg) cyborg;
    if(level == 1)
    {
        return c.cyborgMove(new EasyStrategy(boardobj));
    }
        return c.cyborgMove(new HardStrategy((boardobj)));
  }

  private int maxLimit()
  {
    return (int)pow(boardobj.getBoardSize(),2);
  }

  private int getCheckWinnerEmptyPositionSize()
  {
     return maxLimit()-( (boardobj.getBoardSize() * 2) -1 );
  }
  private void computerPlay() {
    int position = getComputerMove();
    removePositionFromPositionList();
    updateBoard(position, player.getSymbol()==X_SYMBOL?O_SYMBOL_VALUE:X_SYMBOL_VALUE);
    printUserMove("Cyborg");
    checkWinner(cyborg.getSymbol());
  }

  private void checkWinner(char symbol)
  {
    if (boardobj.getEmptyPositions().size()
            <= getCheckWinnerEmptyPositionSize()) {
      isWinningMoveOrTie(symbol);
    }
  }
  private List<Integer> convertPositionIntoRowCol(int position) {
    int row = (position - 1) / boardobj.getBoardSize();
    int col = position - (row * boardobj.getBoardSize()) - 1;
    List<Integer> rowColList = new ArrayList<>();
    rowColList.add(row);
    rowColList.add(col);
    return rowColList;
  }

  private void updateBoard(int position, int symbol) {
    List<Integer> rowCols = convertPositionIntoRowCol(position);
    int row = rowCols.get(0);
    int col = rowCols.get(1);
    boardobj.getBoard().get(row).set(col,symbol);
  }

  private void playerPlay() {
    int position = getPositionFromUser();
    // the last conition of while loop suggests that if the intList does not contains the position
    // choosen by the human then it will again ask him/her
    updateBoard(position, player.getSymbol()==X_SYMBOL?X_SYMBOL_VALUE:O_SYMBOL_VALUE);
    removePositionFromPositionList(position);
    printUserMove(player.getName());
    checkWinner(player.getSymbol());
  }

  private void printUserMove(String name) {
    String result = "~~~~~~~~~~~~~~~~~~~~~~~~~~~  %s's Move  ~~~~~~~~~~~~~~~~~~~~~~~~~~".formatted(name);
    println(result);
    boardobj.showBoard();
  }

  private void removePositionFromPositionList(int position) {
    boardobj.getEmptyPositions().remove(Integer.valueOf(position));
  }

  private void removePositionFromPositionList() {
    boardobj.getEmptyPositions().removeFirst();
  }

  private void manual() {
    println(
        "\uD83C\uDF69 \uD83C\uDF69 Welcome to the classical Tic-Tac-Toe game \uD83C\uDF69 \uD83C\uDF69");
    println(
        "\uD83D\uDCA1 Start the game by placing Xs and Os alternatively\n"
            + "\uD83D\uDCA1 The goal is to get three of your symbols in a row, column or diagonal \n"
            + "\uD83D\uDCA1 The game ends when one player wins or the grid is full, resulting in a TIE\n"
            + "Happy Gaming \uD83C\uDFAE");
  }

  private void rePlay() {
    postStartup();
  }

  private void menu() {
    println(
        "\uD83D\uDCA1 If u wanna replay ♻\uFE0F then press R else press aything ( except power button \uD83D\uDE05 )and Enter to quit ⛩\uFE0F !! ");

    String pref = this.sc.nextLine().toUpperCase();
    if (!pref.isEmpty() && 'R' == pref.charAt(0)) {
      rePlay();
    } else {
      exit(0);
    }
  }

  private void checkRowColDia(List<List<Integer>> board, char symbol) {
    boolean rowTrio = true;
    boolean colTrio = true;
    boolean dia1Trio = true;
    boolean dia2Trio = true;
    int dia1Element = board.getFirst().getFirst();
    int dia2Element = board.getFirst().getLast();
    for (int i = 0; i < board.size(); i++) // checking row and column trio
   {
     int firstRowElement = board.get(i).getFirst();
     int firstColElement = board.getFirst().get(i);
        for(int j = 0;j<board.size();j++)
        {
            int currentElement = board.get(i).get(j);

            rowTrio =  rowTrio && (firstRowElement == currentElement);
            colTrio = colTrio && (firstColElement == board.get(j).get(i));
            if(dia1Trio && i == j)
            {
              dia1Trio = (dia1Element == currentElement);
            }
            if(dia2Trio && i+j+1 == board.size())
            {
              dia2Trio = (dia2Element == currentElement);
            }

        }
        if(rowTrio|| colTrio)
        {
          whoWin(symbol);
          menu();
          return;
        }
        rowTrio = true;
        colTrio = true;
      }
      if(dia1Trio || dia2Trio)
      {
          whoWin(symbol);
          menu();
      }
    }


  private void checkTie() {
    if (boardobj.getEmptyPositions().isEmpty()) {
      String tie = "\uD83D\uDE48 ";
        // checking tie
        String result = tie.repeat(25) +
                "\n" +
                "It's a TIE \uD83D\uDC94";
      println(result);
      menu();
    }
  }

  private void askBoardSize()
  {
    int size = -1;
  do
    {
      try{
      println("\uD83E\uDF95 What size of Tic-Tac-Toe would you liket to play ? We have boards from 3X3 to 15X15, For 7X7 enter 7 and so on ... ");
      size = this.sc.nextInt();
      if(size%2 == 0)
      {
        size = -1;
         throw new EvenNumber(" \uD83D\uDE4F Plz enter odd numbers !");
      }
      else if(size<3 || size >15)
      {
        size = -1;
        throw new OutOfRange(" \uD83D\uDE4F Plz enter numbers between 3 and 15 !");
      }
      }
      catch (EvenNumber | OutOfRange exception)
      {
        println(exception.getMessage());
      }
      finally{
        sc.nextLine();
      }

    }while(size == -1);
   boardobj.setBoardSize(size);
  }

  private void isWinningMoveOrTie(char symbol) {

    List<List<Integer>> board = boardobj.getBoard();
    checkRowColDia(board, symbol);
    checkTie();
  }
}
