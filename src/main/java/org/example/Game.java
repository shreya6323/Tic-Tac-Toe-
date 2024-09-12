package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.System.exit;

public class Game {
  public static final char X_SYMBOL = 'X';
  public static final char O_SYMBOL = 'Y';
  private Board boardobj;
  private Player player;
  private Player cyborg;
  private Scanner sc;

  public Game() {
    this.boardobj = new Board();
    this.player = new Human();
    this.cyborg = new Cyborg();
    sc = new Scanner(System.in);
  }

  private void whoWin(char ch) {
    StringBuilder result = new StringBuilder();
    if (ch == player.getSymbol()) {
      for (int i = 0; i < 25; i++) {
        result.append("\uD83E\uDEC5 ");
      }
      println("");
      result.append(
          String.format(
              "Hurray \uD83E\uDD73, %s you nailed it \uD83E\uDD29 !: ", player.getName()));
      println(result.toString());

    } else {
      for (int i = 0; i < 25; i++) {
        result.append("\uD83E\uDD16 ");
      }
      println("");
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
    do {
      String result =
          String.format("%s choose your playerSymbol from { X, O }: ", player.getName());
      System.out.println(result);
      player.symbol = this.sc.nextLine().toUpperCase().charAt(0);
      if (player.symbol == 'Q') {
        println("Are u leaving me in th middle ?? ohk then \uD83D\uDC4B \uD83D\uDC4B");
        exit(0);
      }

    } while (!(player.symbol == X_SYMBOL || player.symbol == O_SYMBOL)
        && println("Sry, Cannot enter anything except X_SYMBOL or O_SYMBOL \uD83E\uDD25 !"));
  }

  protected void initialStartup() {

    manual();
    setPlayerName();
    postStartup();
  }

  private void postStartup() {
    setPlayerSymbol();
    cyborg.setSymbol((player.getSymbol() == X_SYMBOL) ? O_SYMBOL : X_SYMBOL);
    boardobj.initializeBoard();
    boardobj.showBoard();
    startPlay();
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

        String result =
            String.format(
                "%s enter empty location for the next move from 1 to 9 : ", player.getName());
        System.out.println(result);
        position = this.sc.nextInt();
        if (!(position >= 1 && position <= 9)) {
          position = -1;
          throw new OutOfRange(" \uD83D\uDE4F Plzz enter position between 0 to 9 only !");
        } else if (!boardobj.getEmptyPositions().contains(position)) {
          position = -1;
          throw new OutOfPosition(" \uD83D\uDE4F Plzz enter an empty position !");
        }

      } catch (InputMismatchException e) {
        println(" \uD83D\uDE4F Plzz Enter only numbers  from 1 to 9 !! ");

      } catch (OutOfRange | OutOfPosition e) {
        println(e.getMessage());

      } finally {
        sc.nextLine();
      }

    } while (position == -1);
    return position;
  }

  private int getComputerMove() {
    return boardobj.getEmptyPositions().get(0);
  }

  private void computerPlay() {
    int position = getComputerMove();
    removePositionFromPositionList();
    updateBoard(position, cyborg.getSymbol());
    printUserMove("Cyborg");
    if (boardobj.getEmptyPositions().size() <= 4) {
      isWinningMoveOrTie(player.getSymbol());
    }
  }

  private List<Integer> convertPositionIntoRowCol(int position) {
    int row = (position - 1) / 3;
    int col = position - (row * 3) - 1;
    List<Integer> rowColList = new ArrayList<>();
    rowColList.add(row);
    rowColList.add(col);
    return rowColList;
  }

  private void updateBoard(int position, char symbol) {
    List<Integer> rowCols = convertPositionIntoRowCol(position);
    int row = rowCols.get(0);
    int col = rowCols.get(1);
    boardobj.getBoard()[row][col] = symbol;
  }

  private void playerPlay() {
    int position = getPositionFromUser();
    // the last conition of while loop suggests that if the intList does not contains the position
    // choosen by the human then it will again ask him/her
    updateBoard(position, player.getSymbol());
    removePositionFromPositionList(position);
    printUserMove(player.getName());
    if (boardobj.getEmptyPositions().size() <= 4) {
      isWinningMoveOrTie(player.getSymbol());
    }
  }

  private void printUserMove(String name) {
    String result =
        String.format("~~~~~~~~~~~~~~~~~~~~~~~~~~~  %s's Move  ~~~~~~~~~~~~~~~~~~~~~~~~~~", name);
    println(result);
    boardobj.showBoard();
  }

  private void removePositionFromPositionList(int position) {
    boardobj.getEmptyPositions().remove(Integer.valueOf(position));
  }

  private void removePositionFromPositionList() {

    boardobj.getEmptyPositions().remove(0);
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

  private void checkRowCol(char[][] board, char symbol) {
    for (int i = 0; i < 3; i++) { // checking row and column trio
      if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol)
          || (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
        whoWin(symbol);
        menu();
      }
    }
  }

  private void checkDiagonal(char[][] board, char symbol) {
    if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
        || (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
      whoWin(symbol);
      menu(); // checking diagonal trio
    }
  }

  private void checkTie() {
    if (boardobj.getEmptyPositions().isEmpty()) { // checking tie
      for (int i = 0; i < 20; i++) {
        System.out.println("\uD83D\uDE48 ");
      }
      println("");
      String result = "It's a TIE \uD83D\uDC94";
      println(result);
      menu();
    }
  }

  private void isWinningMoveOrTie(char symbol) {

    char[][] board = boardobj.getBoard();
    checkRowCol(board, symbol);
    checkDiagonal(board, symbol);
    checkTie();
  }
}
